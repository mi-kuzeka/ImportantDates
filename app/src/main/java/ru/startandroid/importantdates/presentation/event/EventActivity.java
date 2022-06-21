package ru.startandroid.importantdates.presentation.event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.core.domain.EventDate;
import ru.startandroid.importantdates.framework.ImportantDatesViewModelFactory;
import ru.startandroid.importantdates.presentation.MainActivity;
import ru.startandroid.importantdates.presentation.customview.MaskedEditText;
import ru.startandroid.importantdates.presentation.helpers.EventAgeHelper;
import ru.startandroid.importantdates.presentation.helpers.EventDateHelper;
import ru.startandroid.importantdates.presentation.helpers.EventValidator;
import ru.startandroid.importantdates.presentation.helpers.ImageHelper;

public class EventActivity extends AppCompatActivity {
    private final String LOG_TAG = EventActivity.class.getSimpleName();

    /**
     * Keys for saving instance state
     */
    private static final String NAME_KEY = "name";
    private static final String DATE_KEY = "date";
    private static final String CATEGORY_KEY = "category";
    private static final String NOTES_KEY = "notes";
    private static final String IS_VIEW_MODE_KEY = "is_view_mode";

    private EventViewModel eventViewModel;

    // Flag for creating new event
    private boolean isNewEvent;
    // Flag for view mode of existing event (can't edit)
    private boolean isViewMode;
    // Current {@link Event} object for existing event
    private Event currentEvent;

    private TextView eventEditorTitle;
    private TextView chooseImageText;

    private TextInputEditText nameEditText;
    private TextInputLayout nameInputLayout;

    private TextInputEditText dateViewText;
    private TextInputLayout dateViewLayout;
    private TextView ageTextView;

    private MaskedEditText dateEditText;
    private TextInputLayout dateInputLayout;

    private TextInputEditText categoryViewText;
    private TextInputLayout categoryViewLayout;

    private MaterialAutoCompleteTextView categoryEditText;
    private TextInputLayout categoryInputLayout;

    private TextInputEditText notesEditText;

    private ImageView bitmapImageView;
    private ImageView backImageView;
    private ImageView deleteEventImageView;
    private ImageView saveEventImageView;
    private ImageView editEventImageView;

    private ImageView changeImageView;
    private ImageView deleteImageView;
    private ImageView rotateLeftImageView;
    private ImageView rotateRightImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            currentEvent = b.getParcelable(MainActivity.EVENT_KEY);
            currentEvent.setImage(b.getParcelable(MainActivity.EVENT_IMAGE_KEY));
        }
        isNewEvent = (currentEvent == null);

        findViewsById();
        if (isNewEvent) {
            eventEditorTitle.setText(R.string.event_fragment_title_new_event);
        } else {
            isViewMode = true;
        }
        initViewModel();
        initValidation(this);
        initBackOnClickListener();
        initSaveOnClickListener(this);
        initImageActions();

        boolean isNewActivity = savedInstanceState == null ||
                savedInstanceState.getString(NAME_KEY) == null;
        if (!isNewActivity) {
            String name = savedInstanceState.getString(NAME_KEY);
            String date = savedInstanceState.getString(DATE_KEY);
            String category = savedInstanceState.getString(CATEGORY_KEY);
            String notes = savedInstanceState.getString(NOTES_KEY);
            Bitmap image = savedInstanceState.getParcelable(MainActivity.EVENT_IMAGE_KEY);
            isViewMode = savedInstanceState.getBoolean(IS_VIEW_MODE_KEY);
            fillViewsForEditMode(name, date, category, notes, image);
        }

        showViewsForCurrentMode();

        if (isNewEvent) {
            nameEditText.requestFocus();
        } else {
            if (isNewActivity) {
                fillViewsForEditMode(currentEvent.getName(),
                        EventDateHelper.getEventDateText(this, currentEvent.getDate()),
                        currentEvent.getCategory().getName(),
                        currentEvent.getNotes(),
                        currentEvent.getBitmapImage());
            }
            fillViewsForViewMode(this);
            initEditOnClickListener();
            initDeleteOnClickListener();
        }
    }

    private void findViewsById() {
        eventEditorTitle = findViewById(R.id.event_editor_title);
        chooseImageText = findViewById(R.id.choose_image_text);

        nameEditText = findViewById(R.id.name_edit_text);
        nameInputLayout = findViewById(R.id.name_text_input);

        dateViewText = findViewById(R.id.date_view_text);
        dateViewLayout = findViewById(R.id.date_view_layout);
        ageTextView = findViewById(R.id.age_text_view);

        dateEditText = findViewById(R.id.date_edit_text);
        dateInputLayout = findViewById(R.id.date_text_input);

        categoryViewText = findViewById(R.id.category_view_text);
        categoryViewLayout = findViewById(R.id.category_view_layout);

        categoryEditText = findViewById(R.id.category_edit_text);
        categoryInputLayout = findViewById(R.id.category_text_input);

        notesEditText = findViewById(R.id.notes_edit_text);

        bitmapImageView = findViewById(R.id.choose_image);
        backImageView = findViewById(R.id.back_to_event_list);
        deleteEventImageView = findViewById(R.id.delete_event);
        saveEventImageView = findViewById(R.id.save_event);
        editEventImageView = findViewById(R.id.edit_event);

        changeImageView = findViewById(R.id.change_image);
        deleteImageView = findViewById(R.id.delete_image);
        rotateLeftImageView = findViewById(R.id.rotate_left);
        rotateRightImageView = findViewById(R.id.rotate_right);
    }

    private void fillViewsForViewMode(Context context) {
        dateViewText.setText(EventDateHelper
                .getEventDateForViewMode(context, currentEvent.getDate()));
        if (currentEvent.hasYear())
            ageTextView.setText(EventAgeHelper.getAgeText(context, currentEvent.getYear()));
        categoryViewText.setText(currentEvent.getCategory().getName());
    }

    private void fillViewsForEditMode(String name,
                                      String date,
                                      String categoryName,
                                      String notes,
                                      Bitmap image) {
        nameEditText.setText(name);
        dateEditText.setText(date);
        categoryEditText.setText(categoryName);
        notesEditText.setText(notes);
        if (image != null)
            bitmapImageView.setImageBitmap(image);
    }

    private void showViewsForCurrentMode() {
        //Set title text
        if (isViewMode) {
            eventEditorTitle.setText(R.string.event_fragment_title_view_event);
        } else {
            eventEditorTitle.setText(R.string.event_fragment_title_edit_event);
        }

        //Set image visibility
        setVisibilityForImageActions();

        nameEditText.setEnabled(!isViewMode);

        int visibleInViewMode = isViewMode ? View.VISIBLE : View.INVISIBLE;
        int invisibleInViewMode = isViewMode ? View.INVISIBLE : View.VISIBLE;

        //Set date visibility
        dateViewLayout.setVisibility(visibleInViewMode);
        ageTextView.setVisibility(visibleInViewMode);
        dateInputLayout.setVisibility(invisibleInViewMode);

        //Set category visibility
        categoryViewLayout.setVisibility(visibleInViewMode);
        categoryInputLayout.setVisibility(invisibleInViewMode);

        //Set notes visibility and hint
        notesEditText.setEnabled(!isViewMode);
        if (isViewMode) {
            notesEditText.setHint("");
        } else {
            notesEditText.setHint(R.string.notes_edit_hint);
        }

        //Set event actions visibility
        int goneInViewMode = isViewMode ? View.GONE : View.VISIBLE;
        deleteEventImageView.setVisibility(goneInViewMode);
        saveEventImageView.setVisibility(goneInViewMode);
        editEventImageView.setVisibility(isViewMode ? View.VISIBLE : View.GONE);
    }

    private void setVisibilityForImageActions() {
        boolean eventHasImage = getImage() != null;
        chooseImageText.setVisibility(
                isViewMode || eventHasImage
                        ? View.INVISIBLE
                        : View.VISIBLE);
        int imageActionsVisibility =
                !isViewMode && eventHasImage
                        ? View.VISIBLE
                        : View.GONE;
        changeImageView.setVisibility(imageActionsVisibility);
        deleteImageView.setVisibility(imageActionsVisibility);
        rotateLeftImageView.setVisibility(imageActionsVisibility);
        rotateRightImageView.setVisibility(imageActionsVisibility);
    }

    private void initViewModel() {
        try {
            ImportantDatesViewModelFactory viewModelFactory =
                    ImportantDatesViewModelFactory.getInstance();
            viewModelFactory.create(EventViewModel.class);

            eventViewModel = new ViewModelProvider(this, viewModelFactory)
                    .get(EventViewModel.class);

            eventViewModel.categories.observe(this, this::fillCategories);

            eventViewModel.category.observe(this, this::saveEvent);

            eventViewModel.loadCategories();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error with loading categories");
        }
    }

    private void fillCategories(List<Category> categories) {
        ArrayList<String> categoryNames = new ArrayList<>();

        String defaultCategory = "";
        if (categories.size() > 0) {
            defaultCategory = categories.get(0).getName();
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.dropdown_list_item, categoryNames);
        categoryEditText.setAdapter(adapter);

        if (isNewEvent) categoryEditText.setText(defaultCategory);
    }

    private void initValidation(Context context) {
        nameEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                EventValidator.validateRequired(context, getEventName(), nameInputLayout);
            }
        });
        dateEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                EventValidator.validateDate(context, getEventDateText(), dateInputLayout);
            }
        });
        categoryEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                EventValidator.validateRequired(context, getCategoryName(),
                        categoryInputLayout);
            }
        });
        //TODO validate with timer
//        nameEditText.addTextChangedListener(new TextValidator(nameEditText) {
//            @Override
//            public void validate(TextView textView, String text) {
//                EventValidator.validateRequired(context, nameEditText.getText(), nameInputLayout);
//            }
//        });
//
//        dateEditText.addTextChangedListener(new TextValidator(dateEditText) {
//            @Override
//            public void validate(TextView textView, String text) {
//                EventValidator.validateDate(context, dateEditText.getRawText(), dateInputLayout);
//            }
//        });
//
//        categoryTextView.addTextChangedListener(new TextValidator(categoryTextView) {
//            @Override
//            public void validate(TextView textView, String text) {
//                EventValidator.validateRequired(context, categoryTextView.getText(),
//                        categoryInputLayout);
//            }
//        });
    }

    /**
     * Check if all fields is valid
     */
    private boolean userInputIsValid(Context context) {
        return EventValidator.validateRequired(context, getEventName(), nameInputLayout) &&
                EventValidator.validateDate(context, getEventDateText(), dateInputLayout) &&
                EventValidator.validateRequired(context, getCategoryName(), categoryInputLayout);
    }

    @Override
    public void onBackPressed() {
        if (!isNewEvent && !eventWasChanged()) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                (dialogInterface, i) -> {
                    // User clicked "Discard" button, close the current activity.
                    finish();
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void initBackOnClickListener() {
        backImageView.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initEditOnClickListener() {
        editEventImageView.setOnClickListener(view -> {
                    isViewMode = false;
                    showViewsForCurrentMode();
                }
        );
    }

    private void initDeleteOnClickListener() {
        deleteEventImageView.setOnClickListener(view ->
                showDeleteEventConfirmationDialog());
    }

    private void initSaveOnClickListener(Context context) {
        saveEventImageView.setOnClickListener(view -> {
                    if (userInputIsValid(context))
                        eventViewModel.getCategoryByName(getCategoryName());
                }
        );
    }

    private void initImageActions() {
        chooseImageText.setOnClickListener(view -> {
            chooseImage();
        });

        changeImageView.setOnClickListener(view -> {
            chooseImage();
        });

        deleteImageView.setOnClickListener(view -> {
            showDeleteImageConfirmationDialog();
        });

        int rotationRightAngle = 90;
        rotateRightImageView.setOnClickListener(view -> {
            Bitmap rotatedImage = ImageHelper.rotateImage(getImage(), rotationRightAngle);
            bitmapImageView.setImageBitmap(rotatedImage);
        });

        int rotationLeftAngle = -90;
        rotateLeftImageView.setOnClickListener(view -> {
            Bitmap rotatedImage = ImageHelper.rotateImage(getImage(), rotationLeftAngle);
            bitmapImageView.setImageBitmap(rotatedImage);
        });
    }

    private void saveEvent(Category category) {
        if (category == null) {
            // Category name of current event
            String categoryName = getCategoryName();
            eventViewModel.addNewCategory(categoryName);
            eventViewModel.getCategoryByName(categoryName);
            return;
        }

        EventDate eventDate = getEventDate();
        // TODO: show alert
        if (eventDate == null) return;

        if (isNewEvent) {
            Event newEvent = new Event(
                    0,
                    getEventName(),
                    eventDate,
                    category,
                    getNotes(),
                    getImage());

            eventViewModel.addEvent(newEvent);
            putParcelableEventToIntent(newEvent);
            setResult(Activity.RESULT_OK, getIntent());
            finish();
        } else {
            if (eventWasChanged()) {
                Event updatedEvent = getUpdatedEvent(category);
                eventViewModel.updateEvent(updatedEvent);
                putParcelableEventToIntent(updatedEvent);
                setResult(Activity.RESULT_OK, getIntent());
            }
            finish();
        }
    }

    private void putParcelableEventToIntent(Event event) {
        Bundle b = new Bundle();
        b.putParcelable(MainActivity.EVENT_KEY, event);
        getIntent().putExtras(b);
        getIntent().putExtra(MainActivity.EVENT_IMAGE_KEY, event.getBitmapImage());
    }

    private Event getUpdatedEvent(Category category) {
        return new Event(currentEvent.getId(),
                getEventName(),
                getEventDate(),
                category,
                getNotes(),
                getImage());
    }

    private boolean eventWasChanged() {
        String currentEventDate =
                EventDateHelper.getEventDateText(this, currentEvent.getDate());

        return !(getEventName().equals(currentEvent.getName())
                && getEventDateText().equals(currentEventDate)
                && getCategoryName().equals(currentEvent.getCategory().getName())
                && getNotes().equals(currentEvent.getNotes())
                && (imageStaySame()));
    }

    private boolean imageStaySame() {
        if (getImage() == null && currentEvent.getBitmapImage() == null) return true;
        if (getImage() == null && currentEvent.getBitmapImage() != null) return false;
        return getImage().equals(currentEvent.getBitmapImage());
    }

    private String getEventName() {
        if (nameEditText.getText() == null) return "";
        return nameEditText.getText().toString().trim();
    }

    private String getEventDateText() {
        return dateEditText.getRawText();
    }

    private EventDate getEventDate() {
        return EventDateHelper.getEventDateObject(this, getEventDateText());
    }

    private String getCategoryName() {
        if (categoryEditText.getText() == null) return "";
        return categoryEditText.getText().toString().trim();
    }

    private String getNotes() {
        if (notesEditText.getText() == null) return "";
        return notesEditText.getText().toString().trim();
    }

    private Bitmap getImage() {
        try {
            if (bitmapImageView.getDrawable() == null) return null;
            BitmapDrawable drawable = (BitmapDrawable) bitmapImageView.getDrawable();
            return drawable.getBitmap();
        } catch (ClassCastException e) {
            return null;
        }
    }

    private void chooseImage() {
        Crop.pickImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Crop.REQUEST_PICK) {
                Uri sourceUri = data.getData();
                Uri destinationUri = Uri.fromFile(
                        new File(getCacheDir(), "cropped")
                );

                Crop.of(sourceUri, destinationUri).asSquare().start(this);

            } else if (requestCode == Crop.REQUEST_CROP) {
                Uri outputUri = Crop.getOutput(data);
                if (outputUri != null) {
                    Bitmap scaledBitmap = ImageHelper.getScaledBitmap(outputUri);

                    bitmapImageView.setImageBitmap(scaledBitmap);
                    setVisibilityForImageActions();
                }
            }
        }
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the event.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteEventConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_event_dialog_msg);
        builder.setPositiveButton(R.string.delete, (dialog, id) -> {
            // User clicked the "Delete" button, so delete the event.
            deleteEvent();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            // User clicked the "Cancel" button, so dismiss the dialog
            // and continue editing the event.
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteImageConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_image_dialog_msg);
        builder.setPositiveButton(R.string.delete, (dialog, id) -> {
            // User clicked the "Delete" button, so delete the image.
            bitmapImageView.setImageBitmap(null);
            setVisibilityForImageActions();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            // User clicked the "Cancel" button, so dismiss the dialog
            // and continue editing the event.
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteEvent() {
        eventViewModel.deleteEvent(currentEvent);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (!isViewMode) {
            outState.putString(NAME_KEY, getEventName());
            outState.putString(DATE_KEY, getEventDateText());
            outState.putString(CATEGORY_KEY, getCategoryName());
            outState.putString(NOTES_KEY, getNotes());
            outState.putParcelable(MainActivity.EVENT_IMAGE_KEY, getImage());
            outState.putBoolean(IS_VIEW_MODE_KEY, isViewMode);
            super.onSaveInstanceState(outState);
        }
    }
}
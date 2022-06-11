package ru.startandroid.importantdates.presentation.event;

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
import android.provider.MediaStore;
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
import java.io.IOException;
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

public class EventActivity extends AppCompatActivity {
    private final String LOG_TAG = EventActivity.class.getSimpleName();

    private EventViewModel eventViewModel;

    // Flag for creating new event
    private boolean isNewEvent;
    // Flag for view mode of existing event (can't edit)
    private boolean isViewMode;
    // Current {@link Event} object for existing event
    private Event currentEvent;
    // Category name of current event
    private String categoryName;

    TextView eventEditorTitle;
    TextView chooseImageText;

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
        initChooseImageOnClickListener();

        if (isNewEvent) {
            nameEditText.requestFocus();
        } else {
            fillViews(this);
            showViewsForCurrentMode();
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
    }

    private void fillViews(Context context) {
        nameEditText.setText(currentEvent.getName());
        nameEditText.setEnabled(true);
        dateViewText.setText(EventDateHelper
                .getEventDateForViewMode(context, currentEvent.getDate()));
        if (currentEvent.hasYear())
            ageTextView.setText(EventAgeHelper.getAgeText(context, currentEvent.getYear()));
        dateEditText.setText(EventDateHelper
                .getEventDateText(context, currentEvent.getDate()));
        categoryViewText.setText(currentEvent.getCategory().getName());
        categoryEditText.setText(currentEvent.getCategory().getName());
        notesEditText.setText(currentEvent.getNotes());
        Bitmap image = currentEvent.getBitmapImage();
        if (image != null) bitmapImageView.setImageBitmap(currentEvent.getBitmapImage());
    }

    private void showViewsForCurrentMode() {
        if (isViewMode) {
            eventEditorTitle.setText(R.string.event_fragment_title_view_event);
        } else {
            eventEditorTitle.setText(R.string.event_fragment_title_edit_event);
        }
        chooseImageText.setVisibility(
                isViewMode || currentEvent.hasImage() ? View.INVISIBLE : View.VISIBLE);
        nameEditText.setEnabled(!isViewMode);
        dateViewLayout.setVisibility(isViewMode ? View.VISIBLE : View.INVISIBLE);
        ageTextView.setVisibility(isViewMode ? View.VISIBLE : View.INVISIBLE);
        dateInputLayout.setVisibility(isViewMode ? View.INVISIBLE : View.VISIBLE);
        categoryViewLayout.setVisibility(isViewMode ? View.VISIBLE : View.INVISIBLE);
        categoryInputLayout.setVisibility(isViewMode ? View.INVISIBLE : View.VISIBLE);
        notesEditText.setEnabled(!isViewMode);
        if (isViewMode) {
            notesEditText.setHint("");
        } else {
            notesEditText.setHint(R.string.notes_edit_hint);
        }
        deleteEventImageView.setVisibility(isViewMode ? View.GONE : View.VISIBLE);
        saveEventImageView.setVisibility(isViewMode ? View.GONE : View.VISIBLE);
        editEventImageView.setVisibility(isViewMode ? View.VISIBLE : View.GONE);
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

    private void initChooseImageOnClickListener() {
        chooseImageText.setOnClickListener(view -> {
            chooseImage();
        });
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
                showDeleteConfirmationDialog());
    }

    private void initSaveOnClickListener(Context context) {
        saveEventImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInputIsValid(context))
                    categoryName = getCategoryName();
                eventViewModel.getCategoryByName(categoryName);
            }
        });
    }

    private void saveEvent(Category category) {
        if (category == null) {
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
                    Bitmap selectedImageBitmap = null;
                    try {
                        selectedImageBitmap =
                                MediaStore.Images.Media.getBitmap(
                                        this.getContentResolver(),
                                        outputUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bitmapImageView.setImageBitmap(selectedImageBitmap);
                    chooseImageText.setVisibility(View.INVISIBLE);
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

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
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

    private void deleteEvent() {
        eventViewModel.deleteEvent(currentEvent);
        setResult(RESULT_OK);
        finish();
    }
}
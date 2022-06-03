package ru.startandroid.importantdates.presentation.event;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.core.domain.Event;
import ru.startandroid.importantdates.core.domain.EventDate;
import ru.startandroid.importantdates.framework.ImportantDatesViewModelFactory;
import ru.startandroid.importantdates.presentation.MainActivity;
import ru.startandroid.importantdates.presentation.customview.MaskedEditText;
import ru.startandroid.importantdates.presentation.helpers.EventDateHelper;
import ru.startandroid.importantdates.presentation.helpers.EventValidator;

public class EventActivity extends AppCompatActivity {
    private final String LOG_TAG = EventActivity.class.getSimpleName();

    private EventViewModel eventViewModel;

    private boolean isNewEvent;
    private Event currentEvent;
    private String categoryName;

    private TextInputEditText nameEditText;
    private TextInputLayout nameInputLayout;

    private MaskedEditText dateEditText;
    private TextInputLayout dateInputLayout;

    private MaterialAutoCompleteTextView categoryEditText;
    private TextInputLayout categoryInputLayout;

    private TextInputEditText notesEditText;

    private ImageView backImageView;
    private ImageView deleteEventImageView;
    private ImageView saveEventImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle b = getIntent().getExtras();
        if (b != null)
            currentEvent = b.getParcelable(MainActivity.EVENT_KEY);
        isNewEvent = (currentEvent == null);

        TextView eventEditorTitle = findViewById(R.id.event_editor_title);
        if (isNewEvent) {
            eventEditorTitle.setText(R.string.event_fragment_title_new_event);
        } else {
            eventEditorTitle.setText(R.string.event_fragment_title_edit_event);
        }

        findViewsById();
        initViewModel();
        initValidation(this);
        initBackOnClickListener();
        initSaveOnClickListener(this);

        if (isNewEvent) {
            nameEditText.requestFocus();
        } else {
            fillViews(this);
            initDeleteOnClickListener();
        }
    }

    private void findViewsById() {
        nameEditText = findViewById(R.id.name_edit_text);
        nameInputLayout = findViewById(R.id.name_text_input);

        dateEditText = findViewById(R.id.date_edit_text);
        dateInputLayout = findViewById(R.id.date_text_input);

        categoryEditText = findViewById(R.id.category_edit_text);
        categoryInputLayout = findViewById(R.id.category_text_input);

        notesEditText = findViewById(R.id.notes_edit_text);

        backImageView = findViewById(R.id.back_to_event_list);
        deleteEventImageView = findViewById(R.id.delete_event);
        saveEventImageView = findViewById(R.id.save_event);
    }

    private void fillViews(Context context) {
        nameEditText.setText(currentEvent.getName());
        dateEditText.setText(EventDateHelper
                .getEventDateText(context, currentEvent.getDate()));
        categoryEditText.setText(currentEvent.getCategory().getName());
        notesEditText.setText(currentEvent.getNotes());
        //TODO set image
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

    private void initDeleteOnClickListener() {
        deleteEventImageView.setVisibility(View.VISIBLE);
//        deleteEventImageView.setOnClickListener(view -> );
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

        //TODO: get image

        if (isNewEvent) {
            Event newEvent = new Event(0, getEventName(), eventDate, category, getNotes());
            eventViewModel.addEvent(newEvent);
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            if (eventWasChanged()) {
                Event updatedEvent = getUpdatedEvent(category);
                eventViewModel.updateEvent(updatedEvent);
                setResult(Activity.RESULT_OK);
            }
            finish();
        }
    }

    private Event getUpdatedEvent(Category category) {
        return new Event(currentEvent.getId(),
                getEventName(),
                getEventDate(),
                category,
                getNotes(),
                //TODO get image
                null);
    }

    private boolean eventWasChanged() {
        String currentEventDate =
                EventDateHelper.getEventDateText(this, currentEvent.getDate());
        return !(getEventName().equals(currentEvent.getName())
                && getEventDateText().equals(currentEventDate)
                && getCategoryName().equals(currentEvent.getCategory().getName())
                && getNotes().equals(currentEvent.getNotes()));
        //TODO && getBitmapImage().equals(currentEvent.getBitmapImage()))
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
}
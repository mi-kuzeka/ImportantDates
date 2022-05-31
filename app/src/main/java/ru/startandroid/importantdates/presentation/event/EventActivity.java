package ru.startandroid.importantdates.presentation.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

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
import ru.startandroid.importantdates.presentation.customview.MaskedEditText;
import ru.startandroid.importantdates.presentation.helpers.EventDateHelper;
import ru.startandroid.importantdates.presentation.helpers.EventValidator;

public class EventActivity extends AppCompatActivity {
    private final String LOG_TAG = EventActivity.class.getSimpleName();

    private EventViewModel eventViewModel;

    private boolean isNewEvent;
    private Uri currentEventUri;
    private Event currentEvent;
    private Category category;
    private boolean categoryIsReceived;
    private String categoryName;

    private TextInputEditText nameEditText;
    private TextInputLayout nameInputLayout;

    private MaskedEditText dateEditText;
    private TextInputLayout dateInputLayout;

    private MaterialAutoCompleteTextView categoryTextView;
    private TextInputLayout categoryInputLayout;

    private ImageView backImageView;
    private ImageView deleteEventImageView;
    private ImageView saveEventImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        currentEventUri = getIntent().getData();
        isNewEvent = (currentEventUri == null);

        if (isNewEvent) {
            this.setTitle(R.string.event_fragment_title_new_event);
        } else {
            this.setTitle(R.string.event_fragment_title_edit_event);
        }

        findViewsById();
        initViewModel();
        initValidation(this);
        initBackOnClickListener();
        initSaveOnClickListener(this);

        if (isNewEvent) {
            nameEditText.requestFocus();
        } else {
            initDeleteOnClickListener();
        }
    }

    private void findViewsById() {
        nameEditText = findViewById(R.id.name_edit_text);
        nameInputLayout = findViewById(R.id.name_text_input);

        dateEditText = findViewById(R.id.date_edit_text);
        dateInputLayout = findViewById(R.id.date_text_input);

        categoryTextView = findViewById(R.id.category_edit_text);
        categoryInputLayout = findViewById(R.id.category_text_input);

        backImageView = findViewById(R.id.back_to_event_list);
        deleteEventImageView = findViewById(R.id.delete_event);
        saveEventImageView = findViewById(R.id.save_event);
    }

    private void initViewModel() {
        try {
            ImportantDatesViewModelFactory viewModelFactory =
                    ImportantDatesViewModelFactory.getInstance();
            viewModelFactory.create(EventViewModel.class);

            eventViewModel = new ViewModelProvider(this, viewModelFactory)
                    .get(EventViewModel.class);

            eventViewModel.categories.observe(this, this::fillCategories);

            categoryIsReceived = false;
            eventViewModel.category.observe(this, this::setCategoryAndSaveEvent);

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
        categoryTextView.setAdapter(adapter);

        if (isNewEvent) categoryTextView.setText(defaultCategory);
    }

    private void initValidation(Context context) {
        nameEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                EventValidator.validateRequired(context, nameEditText.getText(), nameInputLayout);
            }
        });
        dateEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                EventValidator.validateDate(context, dateEditText.getRawText(), dateInputLayout);
            }
        });
        categoryTextView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                EventValidator.validateRequired(context, categoryTextView.getText(),
                        categoryInputLayout);
            }
        });
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
        return EventValidator.validateRequired(context, nameEditText.getText(), nameInputLayout) &&
                EventValidator.validateDate(context, dateEditText.getRawText(), dateInputLayout) &&
                EventValidator.validateRequired(context, categoryTextView.getText(), categoryInputLayout);
    }

    private void initBackOnClickListener() {
        backImageView.setOnClickListener(view -> finish());
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
                    categoryName  = categoryTextView.getText().toString().trim();
                    eventViewModel.getCategoryByName(categoryName);
            }
        });
    }

    private void setCategoryAndSaveEvent(Category c) {
        categoryIsReceived = true;
        category = c;

        saveEvent();
    }

    private void saveEvent() {
        String eventName = nameEditText.getText().toString().trim();
        EventDate eventDate = EventDateHelper.getEventDateObject(this,
                dateEditText.getRawText());
        if (category == null) {
            eventViewModel.addNewCategory(categoryName);
        }
        if (isNewEvent) {

        }
    }
}
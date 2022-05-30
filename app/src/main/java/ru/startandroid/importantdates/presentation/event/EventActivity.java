package ru.startandroid.importantdates.presentation.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.framework.ImportantDatesViewModelFactory;
import ru.startandroid.importantdates.presentation.customview.MaskedEditText;
import ru.startandroid.importantdates.presentation.helpers.EventValidator;
import ru.startandroid.importantdates.presentation.helpers.TextValidator;

public class EventActivity extends AppCompatActivity {
    private final String LOG_TAG = EventActivity.class.getSimpleName();

    private EventViewModel eventViewModel;

    private boolean isNewEvent;
    private Uri currentEventUri;

    private TextInputEditText nameEditText;
    private TextInputLayout nameInputLayout;

    private MaskedEditText dateEditText;
    private TextInputLayout dateInputLayout;

    private MaterialAutoCompleteTextView categoryTextView;
    private TextInputLayout categoryInputLayout;

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

        initViewModel();
        initValidation(this);
    }

    private void initViewModel() {
        try {
            ImportantDatesViewModelFactory viewModelFactory =
                    ImportantDatesViewModelFactory.getInstance();
            viewModelFactory.create(EventViewModel.class);

            eventViewModel = new ViewModelProvider(this, viewModelFactory)
                    .get(EventViewModel.class);

            eventViewModel.categories.observe(this,
                    this::fillCategories);

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
        nameEditText = findViewById(R.id.name_edit_text);
        nameInputLayout = findViewById(R.id.name_text_input);

        dateEditText = findViewById(R.id.date_edit_text);
        dateInputLayout = findViewById(R.id.date_text_input);

        categoryTextView = findViewById(R.id.category_edit_text);
        categoryInputLayout = findViewById(R.id.category_text_input);

        nameEditText.addTextChangedListener(new TextValidator(nameEditText) {
            @Override
            public void validate(TextView textView, String text) {
                EventValidator.validateRequired(context, nameEditText.getText(), nameInputLayout);
            }
        });

        dateEditText.addTextChangedListener(new TextValidator(dateEditText) {
            @Override
            public void validate(TextView textView, String text) {
                EventValidator.validateDate(context, dateEditText.getRawText(), dateInputLayout);
            }
        });

        categoryTextView.addTextChangedListener(new TextValidator(categoryTextView) {
            @Override
            public void validate(TextView textView, String text) {
                EventValidator.validateRequired(context, categoryTextView.getText(),
                        categoryInputLayout);
            }
        });

    }

    /**
     * Check if all fields is valid
     */
    private boolean userInputIsValid(Context context) {
        return EventValidator.validateRequired(context, nameEditText.getText(), nameInputLayout) &&
                EventValidator.validateDate(context, dateEditText.getRawText(), dateInputLayout) &&
                EventValidator.validateRequired(context, categoryTextView.getText(), categoryInputLayout);
    }
}
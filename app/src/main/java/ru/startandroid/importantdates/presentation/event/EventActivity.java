package ru.startandroid.importantdates.presentation.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ru.startandroid.importantdates.R;
import ru.startandroid.importantdates.presentation.customview.MaskedEditText;
import ru.startandroid.importantdates.presentation.helpers.EventValidator;
import ru.startandroid.importantdates.presentation.helpers.TextValidator;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        validateUserInput(this);
    }

    private void validateUserInput(Context context) {
        TextInputEditText nameEditText = findViewById(R.id.name_edit_text);
        TextInputLayout nameInputLayout = findViewById(R.id.name_text_input);

        MaskedEditText dateEditText = findViewById(R.id.date_edit_text);
        TextInputLayout dateInputLayout = findViewById(R.id.date_text_input);

        MaterialAutoCompleteTextView categoryTextView = findViewById(R.id.category_edit_text);
        TextInputLayout categoryInputLayout = findViewById(R.id.category_text_input);

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
}
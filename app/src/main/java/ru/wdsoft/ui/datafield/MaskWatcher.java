package ru.wdsoft.ui.datafield;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by slaventii@mail.ru on 08.06.2018.
 */
public class MaskWatcher implements TextWatcher {
    private boolean isRunning = false;
    private boolean isDeleting = false;
    private final String mask;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    public static MaskWatcher buildCpf() {
        return new MaskWatcher("###.###.###-##");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable.toString() == null || editable.toString().isEmpty()){
            return;
        }

        if (isRunning || isDeleting) {
            return;
        }

        isRunning = true;

        int editableLength = editable.length();
        if (editableLength < mask.length()) {
            if (mask.charAt(editableLength) != '#') {
                editable.append(mask.charAt(editableLength));
            } else if (mask.charAt(editableLength-1) != '#') {
                editable.insert(editableLength-1, mask, editableLength-1, editableLength);
            }
        }

        isRunning = false;
    }
}
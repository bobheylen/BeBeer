package be.kuleuven.bebeer.ui.pickup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PikupViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PikupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package be.kuleuven.bebeer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.kuleuven.bebeer.databinding.FragmentPikupBinding;

public class PickUpFragment extends Fragment {

    private FragmentPikupBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PikupViewModel homeViewModel =
                new ViewModelProvider(this).get(PikupViewModel.class);

        binding = FragmentPikupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
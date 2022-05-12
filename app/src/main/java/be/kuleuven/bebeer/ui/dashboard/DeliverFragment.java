package be.kuleuven.bebeer.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.kuleuven.bebeer.databinding.FragmentDeliverBinding;

public class DeliverFragment extends Fragment {

    private FragmentDeliverBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DeliverViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DeliverViewModel.class);

        binding = FragmentDeliverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
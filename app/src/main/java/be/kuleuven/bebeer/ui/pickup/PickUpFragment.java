package be.kuleuven.bebeer.ui.pickup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.placePickupActivity;
import be.kuleuven.bebeer.pickups.GetPickupActivity;
import be.kuleuven.bebeer.databinding.FragmentPickupBinding;

public class PickUpFragment extends Fragment {

    private FragmentPickupBinding binding;
    private ImageButton btnGetPickup, btnMakePickup;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PikupViewModel homeViewModel =
                new ViewModelProvider(this).get(PikupViewModel.class);

        binding = FragmentPickupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnGetPickup = (ImageButton) root.findViewById(R.id.btnGetPikup);
        btnMakePickup = (ImageButton) root.findViewById(R.id.btnMakePikup);

        btnGetPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGetPikupActivity();
            }
        });

        btnMakePickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMakePikupActivity();
            }
        });

        return root;
    }

    public void openMakePikupActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), GetPickupActivity.class);
        startActivity(intent);
    }


    public void openGetPikupActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), placePickupActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
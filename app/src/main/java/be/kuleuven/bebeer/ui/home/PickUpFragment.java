package be.kuleuven.bebeer.ui.home;

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
import be.kuleuven.bebeer.activities.GetPickupActivity;
import be.kuleuven.bebeer.databinding.FragmentPikupBinding;

public class PickUpFragment extends Fragment {

    private FragmentPikupBinding binding;
    // zelf geschrven
    private ImageButton btnGetPikup, btnMakePikup;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PikupViewModel homeViewModel =
                new ViewModelProvider(this).get(PikupViewModel.class);

        binding = FragmentPikupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //zelf geschreven
        btnGetPikup = (ImageButton) root.findViewById(R.id.btnGetPikup);
        btnMakePikup = (ImageButton) root.findViewById(R.id.btnMakePikup);




        btnGetPikup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGetPikupActivity();
            }
        });

        btnMakePikup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openMakePikupActivity();
            }
        });

        // tot heir


        return root;
    }

    public void openMakePikupActivity(){
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
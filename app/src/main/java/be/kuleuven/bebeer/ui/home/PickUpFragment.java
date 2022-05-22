package be.kuleuven.bebeer.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.kuleuven.bebeer.MapsActivity;
import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.GetPikupActivity;
import be.kuleuven.bebeer.activities.HomePage2Activity;
import be.kuleuven.bebeer.activities.LoginActivity;
import be.kuleuven.bebeer.activities.OrderActivity;
import be.kuleuven.bebeer.databinding.FragmentPikupBinding;

public class PickUpFragment extends Fragment {

    private FragmentPikupBinding binding;
    // zelf geschrven
    private Button btnGetPikup;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PikupViewModel homeViewModel =
                new ViewModelProvider(this).get(PikupViewModel.class);

        binding = FragmentPikupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //zelf geschreven
        btnGetPikup = (Button) root.findViewById(R.id.btnGetPikup);



        btnGetPikup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGetPikupActivity();
            }
        });

        // tot heir


        return root;
    }



    public void openGetPikupActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), GetPikupActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
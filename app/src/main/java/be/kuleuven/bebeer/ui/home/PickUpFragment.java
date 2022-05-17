package be.kuleuven.bebeer.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.databinding.FragmentPikupBinding;

public class PickUpFragment extends Fragment {

    private FragmentPikupBinding binding;

    private Button btnPicupPI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PikupViewModel homeViewModel =
                new ViewModelProvider(this).get(PikupViewModel.class);

        binding = FragmentPikupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnPicupPI = root.findViewById(R.id.btnPicupPI);
        btnPicupPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:50.878480,4.699855"));
                Intent chooser = Intent.createChooser(intent,"Launch Maps");
                startActivity(chooser);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
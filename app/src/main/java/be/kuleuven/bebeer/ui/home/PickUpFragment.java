package be.kuleuven.bebeer.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import be.kuleuven.bebeer.MapsActivity;
import be.kuleuven.bebeer.R;
import be.kuleuven.bebeer.activities.HomePage2Activity;
import be.kuleuven.bebeer.databinding.FragmentPikupBinding;

public class PickUpFragment extends Fragment {

    private FragmentPikupBinding binding;

    private Button btnPicupPI;
    private Button btnPikup;
    private static final String TAG = "LoginActivity"; // een log maaken
    private static final int ERROR_DIALOG_REQUEST = 9001; // als de versie niet goed is.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PikupViewModel homeViewModel =
                new ViewModelProvider(this).get(PikupViewModel.class);

        binding = FragmentPikupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnPicupPI = root.findViewById(R.id.btnOpenMap);
        btnPikup = (Button) root.findViewById(R.id.btnOpenMap);

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

    private void init(){
        btnPikup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    // dient gewoon om de vertie te cheken en een bericht te stuuren met wat je moet doen als het niet werkt
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services verion");
    /*
        int available = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(PickUpFragment.this);

        if(available == ConnectionResult.SUCCESS){
            // ales is oke de user kan een map request doen
            Log.d(TAG, "isServicesOK: Google Play Services is working");
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            // er is een fout maar je kan het oplosen
            Log.d(TAG, "isServicesOK: er is een probleem maar je kan het fixen");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(PickUpFragment.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "je kan geen map request doen", Toast.LENGTH_SHORT)
                    .show();
        }
        */
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
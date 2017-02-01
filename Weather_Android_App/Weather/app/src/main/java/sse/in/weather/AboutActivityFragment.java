package sse.in.weather;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutActivityFragment extends Fragment {

    private sse.in.weather.AboutActivityFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);

        binding.setClickListener(this);

        return binding.getRoot();
    }

    @SuppressWarnings("unused")
    public void onRiseClicked(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rise-world.com"));
        startActivity(browserIntent);
    }

    @SuppressWarnings("unused")
    public void onSseClicked(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sseptp.org"));
        startActivity(browserIntent);
    }
}

package mobilesystems.gps.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import mobilesystems.gps.R;

public class PerksView extends Fragment {

    Button btn_redeem1, btn_redeem2, btn_redeem3;
    TextView textView_discount1, textView_discount2, textView_discount3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.perks_view, container, false);

        btn_redeem1 = view.findViewById(R.id.btn_redeem1);
        btn_redeem2 = view.findViewById(R.id.btn_redeem2);
        btn_redeem3 = view.findViewById(R.id.btn_redeem3);
        textView_discount1 = view.findViewById(R.id.textView_discount1);
        textView_discount2 = view.findViewById(R.id.textView_discount2);
        textView_discount3 = view.findViewById(R.id.textView_discount3);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_redeem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Discount redeemed!", Toast.LENGTH_SHORT).show();
                textView_discount1.setVisibility(View.GONE);
                btn_redeem1.setVisibility(View.GONE);
            }
        });

        btn_redeem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Discount redeemed!", Toast.LENGTH_SHORT).show();
                textView_discount2.setVisibility(View.GONE);
                btn_redeem2.setVisibility(View.GONE);
            }
        });

        btn_redeem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Insufficient funds!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

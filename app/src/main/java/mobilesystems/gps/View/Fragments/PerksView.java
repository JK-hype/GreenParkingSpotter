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

import mobilesystems.gps.Acquaintance.IUser;
import mobilesystems.gps.Acquaintance.SessionData;
import mobilesystems.gps.R;

public class PerksView extends Fragment {

    Button btn_redeem1, btn_redeem2, btn_redeem3;
    TextView txt_discount1, txt_discount2, txt_discount3, coins;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.perks_view, container, false);

        btn_redeem1 = view.findViewById(R.id.btn_redeem1);
        btn_redeem2 = view.findViewById(R.id.btn_redeem2);
        btn_redeem3 = view.findViewById(R.id.btn_redeem3);
        txt_discount1 = view.findViewById(R.id.textView_discount1);
        txt_discount2 = view.findViewById(R.id.textView_discount2);
        txt_discount3 = view.findViewById(R.id.textView_discount3);
        coins = view.findViewById(R.id.txt_perks_coins);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final IUser user = SessionData.getInstance().getCurrentUser();
        coins.setText(user.getCoins() + " coins");

        btn_redeem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.payCoins(PerkPrice.LOW.getPrice())) {
                    Toast.makeText(getContext(), "Discount redeemed!", Toast.LENGTH_SHORT).show();
                    txt_discount1.setVisibility(View.GONE);
                    btn_redeem1.setVisibility(View.GONE);
                    coins.setText(user.getCoins() + " coins");
                } else {
                    Toast.makeText(getContext(), "Insufficient funds!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_redeem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.payCoins(PerkPrice.MEDIUM.getPrice())) {
                    Toast.makeText(getContext(), "Discount redeemed!", Toast.LENGTH_SHORT).show();
                    txt_discount2.setVisibility(View.GONE);
                    btn_redeem2.setVisibility(View.GONE);
                    coins.setText(user.getCoins() + " coins");
                } else {
                    Toast.makeText(getContext(), "Insufficient funds!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_redeem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.payCoins(PerkPrice.HIGH.getPrice())) {
                    Toast.makeText(getContext(), "Discount redeemed!", Toast.LENGTH_SHORT).show();
                    txt_discount3.setVisibility(View.GONE);
                    btn_redeem3.setVisibility(View.GONE);
                    coins.setText(user.getCoins() + " coins");
                } else {
                    Toast.makeText(getContext(), "Insufficient funds!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

enum PerkPrice {
    LOW(25), MEDIUM(50), HIGH(75);

    private int price;

    public int getPrice() {
        return this.price;
    }

    private PerkPrice(int price) {
        this.price = price;
    }
}
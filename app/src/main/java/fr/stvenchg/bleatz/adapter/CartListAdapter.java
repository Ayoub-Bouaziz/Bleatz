package fr.stvenchg.bleatz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.panier.CartResponse;
import fr.stvenchg.bleatz.api.panier.CreateMenuResponse;
import fr.stvenchg.bleatz.api.panier.DeleteFromCartResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartListAdapter extends BaseAdapter {

        private List<CartResponse.MenuContent> menuContent;
        private Context context ;

    List<CartResponse.MenuItem> menuItems = new ArrayList<>();
    private int idMenu;

        public CartListAdapter(List<CartResponse.MenuContent> menuContent, Context context) {
            this.menuContent = menuContent;
            this.context = context;
        }
        @Override
        public int getCount() {
            return menuContent.size();
        }

        @Override
        public Object getItem(int position) {
            return menuContent.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart, parent, false);
            }

            for (CartResponse.MenuContent menuContent :menuContent ) {

                if (menuContent.getContent()!=null)
                for (CartResponse.MenuItem menuItemm : menuContent.getContent()) {
                    menuItems.add(menuItemm);
                }
            }


            CartResponse.MenuItem menuItem = menuItems.get(position);

            TextView tvMenuItemNameBurger = convertView.findViewById(R.id.nom_menu);
            TextView tvMenuItemPrice = convertView.findViewById(R.id.prix_menu);
            ImageView tvMenuItemImage= convertView.findViewById(R.id.image_menu);
            TextView tvMenuItemNameBoisson = convertView.findViewById(R.id.nom_Boisson);
            Button btMenuItemDelete = convertView.findViewById(R.id.btn_delete);


            tvMenuItemNameBurger.setText(menuItem.getBurger().getNom());
            tvMenuItemNameBoisson.setText(menuItem.getBoisson().getNom());

            tvMenuItemPrice.setText(menuItem.getPrix()+"0 €");

            Picasso.get()
                    .load("https://api.stevenching.fr/bleatz/img/burger/" + menuItem.getBurger().getImage())
                    .into(tvMenuItemImage);
            btMenuItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AuthenticationManager authenticationManager = new AuthenticationManager(context);
                    String accessToken = authenticationManager.getAccessToken();

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    retrofit2.Call<DeleteFromCartResponse> call = apiInterface.deleteFromCart("Bearer " + accessToken, menuContent.get(position).getIdMenu());
                    call.enqueue(new Callback<DeleteFromCartResponse>() {
                        @Override
                        public void onResponse(Call<DeleteFromCartResponse> call, Response<DeleteFromCartResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Le menu" + menuItem.getBurger().getNom() + " a été supprimé de votre panier ", Toast.LENGTH_SHORT).show();
                            //  refreshView(position);
                            } else {
                                Toast.makeText(context, "Le menu" + menuItem.getBurger().getNom() + " n'a été trouvé dans votre panier ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteFromCartResponse> call, Throwable t) {

                        }

                    });
                }
            });


            return convertView;
        }
    public void refreshView(int position) {
        if (menuItems != null && !menuItems.isEmpty() && position >= 0 && position < menuItems.size()) {
            menuItems.remove(position);
            notifyDataSetChanged();
        }
    }

}

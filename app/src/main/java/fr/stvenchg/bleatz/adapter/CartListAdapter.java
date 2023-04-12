package fr.stvenchg.bleatz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.panier.CartResponse;


public class CartListAdapter extends BaseAdapter {

        private List<CartResponse.MenuItem> menuItems;

        public CartListAdapter(List<CartResponse.MenuItem> menuItems) {
            this.menuItems = menuItems;


        }
        @Override
        public int getCount() {
            return menuItems.size();
        }

        @Override
        public Object getItem(int position) {
            return menuItems.get(position);
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


            CartResponse.MenuItem menuItem = menuItems.get(position);

            TextView tvMenuItemNameBurger = convertView.findViewById(R.id.nom_menu);
            TextView tvMenuItemPrice = convertView.findViewById(R.id.prix_menu);
            ImageView tvMenuItemImage= convertView.findViewById(R.id.image_menu);
            TextView tvMenuItemNameBoisson = convertView.findViewById(R.id.nom_Boisson);


            tvMenuItemNameBurger.setText(menuItem.getBurger().getNom());
            tvMenuItemNameBoisson.setText(menuItem.getBoisson().getNom());

            tvMenuItemPrice.setText(menuItem.getPrix()+"0 €");

            Picasso.get()
                    .load("https://api.stevenching.fr/bleatz/img/burger/" + menuItem.getBurger().getImage())
                    .into(tvMenuItemImage);


            return convertView;
        }
    }

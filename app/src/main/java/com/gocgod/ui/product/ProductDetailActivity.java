package com.gocgod.ui.product;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gocgod.ApiService;
import com.gocgod.R;
import com.gocgod.ServiceGenerator;
import com.gocgod.WrapContentViewPager;
import com.gocgod.adapter.DescriptionTestimonialAdapter;
import com.gocgod.cart.CartDataSource;
import com.gocgod.model.ProductData;
import com.gocgod.model.ProductTestimonial;
import com.gocgod.model.ResponseSuccess;
import com.gocgod.ui.BaseActivity;
import com.gocgod.ui.Global;
import com.gocgod.ui.transaction.BadgeDrawable;
import com.gocgod.ui.transaction.CartActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.pager)
    WrapContentViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.quantity)
    carbon.widget.EditText quantity;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.loadingLayout)
    carbon.widget.LinearLayout loadingLayout;
    @BindView(R.id.loading)
    carbon.widget.ProgressBar loading;
    @BindView(R.id.loadingText)
    carbon.widget.TextView loadingText;
//    @BindView(R.id.expand_text_view)
//    ExpandableTextView comment;

    private int cartCount;
    private FragmentManager fragmentManager;
//    private ProductData data;
    private String deskripsi;
    private int productId;
    private ProductData data;
    private ArrayList<ProductTestimonial> productTestimonial = new ArrayList<ProductTestimonial>();
//    private Context context;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client2;

//    public ProductDetailActivity(Context context){
//        this.context = context;
//    }

    public ProductDetailActivity(){super();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        Global.setupUI(findViewById(R.id.layout), ProductDetailActivity.this, quantity);

        loadingLayout.setVisibility(View.VISIBLE);

        fragmentManager = getSupportFragmentManager();

        Bundle bundle = getIntent().getExtras();
        productId = bundle.getInt("productId");

        buildToolbar("Detail Produk");
        buildDrawer(savedInstanceState, toolbar);

        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Manimpilasi sedikit untuk set TextColor pada Tab
//        tabs.setTabTextColors(getResources().getColor(R.color.colorPrimary),
//                getResources().getColor(android.R.color.white));

        //pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener());

        //set tab ke ViewPager
        tabs.setupWithViewPager(pager);

        //konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        if (bundle != null) {
            getProductData(Integer.toString(productId));
            //set object adapter kedalam ViewPager
//            Log.d("lapar", "a");
//            Log.d("maumakan", "lllll");
//            String deskripsi = data.getDescription().replace("\n", "\\n").replace("\r", "\\r");
            //Log.d("lapar", data.toString());

            //pager.setAdapter(new DescriptionTestimonialAdapter(getSupportFragmentManager(),
            //        deskripsi));
        }
        new FetchCartCountTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        try {
            cartCount = Global.getCartCount(this);
        } catch (SQLException e) {
            Log.d("sqlError", e.getMessage());
        }

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(this);
        }

        badge.setCount(cartCount);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);

        IconicsDrawable cartIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_shopping_cart);
        cartIcon = cartIcon.color(Color.WHITE);
        icon.setDrawableByLayerId(R.id.ic_shoppingcart, cartIcon.actionBar());

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchCartCountTask().execute();
    }

    class FetchCartCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int cartCount = 0;
            try {
                cartCount = Global.getCartCount(ProductDetailActivity.this);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return cartCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            cartCount = count;
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case android.R.id.home:
                finish(); break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getProductData(final String productId) {
        ApiService client = ServiceGenerator.createService(ApiService.class);

        //Ambil data produk
        Call<ResponseSuccess> call = client.getProductDetail(productId);
        call.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                ResponseSuccess result = response.body();

                data = result.getSuccess().getData().getProductData();
                //Log.d("aurel", data.getDescription().replace("\n", "\\n").replace("\r", "\\r"));
                //bentuk huruf
                Typeface fontTitle = Typeface.createFromAsset(getAssets(), "fonts/nexablack.ttf");
                Typeface fontDescription = Typeface.createFromAsset(getAssets(), "fonts/mm.ttf");

                //format rupiah
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
                formatRp.setCurrencySymbol("Rp ");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');
                kursIndonesia.setDecimalFormatSymbols(formatRp);

                if (data != null) {
                    Picasso.with(ProductDetailActivity.this).load(Global.imgProduct + data.getCategoryName() + "/" + data.getPicture()).resize(700, 750).into(image);
//            name.setText(Integer.toString(bundle.getInt("productVarianId")));
//            name.setTypeface(font);
                    String varianName = data.getVarianName();
                    name.setTypeface(fontTitle);
                    name.setText(varianName);
//                    Log.d("maumakan", );

                    double harga = Double.parseDouble(data.getPrice());
                    price.setText(kursIndonesia.format(harga));

                    deskripsi = data.getDescription().replace("\r", "");

                    getProductTestimonial(productId);

////                    Fragment descriptionFragment = new DescriptionProduct();
//                    Intent intent = new Intent(ProductDetailActivity.this, DescriptionTestimonialAdapter.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("a", data.getDescription());
////                    descriptionFragment.setArguments(bundle);
//                    intent.putExtras(bundle);
//                    Log.d("hai", bundle.getString("a"));
////                    intent.putExtra("a", "hai");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    ProductDetailActivity.this.startActivity(intent);
//                    startActivity(intent);


//                    DescriptionProduct FragmentDescription = new DescriptionProduct();
//                    FragmentDescription.setArguments(bundle);

//                    String productDescription = data.getDescription();
//                    description.setText(productDescription);

//                    comment.setText("ini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfj ini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfjini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfjini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfjini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfjini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfjini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfjini contoh comment afdjklasdjfkladskfljasfj alkdfjlasjdfklasjfkljakldsfjkdasjf adsjlkfjasldkfjaksdfjlkasjdfkljaslkdfjakld jadslkfjasldfjalskd jadslfkjasdlkfjadsklfjkalsdfjkajskdlfj");

                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                refresh();
            }
        });
    }

    public void getProductTestimonial(final String productId)
    {
        ApiService client = ServiceGenerator.createService(ApiService.class);
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(1));

        //Ambil data testimoni produk
        Call<ResponseSuccess> callProductTestimonial = client.getProductTestimonial(productId, data);
        callProductTestimonial.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                ResponseSuccess result = response.body();
                int total = result.getSuccess().getData().getProductTestimonialPagination().getTotal();

                if (total > 0) {
                    productTestimonial.addAll(result.getSuccess().getData().getProductTestimonialPagination().getData());

                    //pager.setAdapter(new DescriptionTestimonialAdapter(getSupportFragmentManager(),deskripsi, productTestimonial, true, productId));
                }
                pager.setAdapter(new DescriptionTestimonialAdapter(getSupportFragmentManager(),deskripsi, productTestimonial, productId));
                loadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                refresh();
            }
        });
    }

    public void refresh()
    {
        loading.setVisibility(View.GONE);
        loadingText.setText(getResources().getString(R.string.error));
        String message = getResources().getString(R.string.load_error);
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Ulangi", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.setVisibility(View.VISIBLE);
                        loadingText.setText(getResources().getString(R.string.loading));
                        getProductData(String.valueOf(productId));
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
    }

    @OnTouch(R.id.quantity)
    public boolean onTouch()
    {
        Global.showCursor(quantity);
        return false;
    }

    @OnClick(R.id.btn_add_to_cart)
    public void addToCart() {
        String tmp = quantity.getText().toString();
        if(tmp.equals(""))
        {
            quantity.setText(String.valueOf(1));
            String message = getResources().getString(R.string.minimum_qty);
            Global.showSnackBar(findViewById(R.id.layout), message, Color.WHITE);
            return;
        }
        else {
            int quantity = Integer.parseInt(tmp);
            if (quantity < 1) {
                this.quantity.setText(String.valueOf(1));
                String message = getResources().getString(R.string.minimum_qty);
                Global.showSnackBar(findViewById(R.id.layout), message, Color.WHITE);
                return;
            }
        }

        //kalo quantity >= 1, masukkin cart
        int quantity = Integer.parseInt(tmp);

        CartDataSource dataSource = new CartDataSource(this);

        try {
            dataSource.open();

            dataSource.createCart(data.getVarianId(), data.getVarianName(), data.getPicture(), data.getCategoryName(), quantity, Double.valueOf(data.getWeight()), Double.valueOf(data.getPrice()), 0, 0, 0, "");

            new FetchCartCountTask().execute();

            String message = getResources().getString(R.string.added_to_cart);

            Snackbar snackbar = Snackbar
                    .make(layout, message, Snackbar.LENGTH_LONG)
                    .setAction("CART", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);

            snackbar.show();
        }
        catch (Exception e)
        {
            Log.d("sqlError", e.getMessage());
        }
        finally {
            dataSource.close();
        }
    }

    @OnClick(R.id.increase)
    public void increase()
    {
        String tmp = quantity.getText().toString();
        if(tmp.equals("")) {
            quantity.setText(String.valueOf(1));
        }
        else
        {
            int quantity = Integer.parseInt(tmp);
            quantity++;
            this.quantity.setText(String.valueOf(quantity));
        }
    }

    @OnClick(R.id.decrease)
    public void decrease()
    {
        String tmp = quantity.getText().toString();
        if(tmp.equals("")) {
            quantity.setText(String.valueOf(1));
        }
        else
        {
            int quantity = Integer.parseInt(tmp);
            if(quantity <= 1)
                this.quantity.setText(String.valueOf(1));
            else {
                quantity--;
                this.quantity.setText(String.valueOf(quantity));
            }
        }
    }
}

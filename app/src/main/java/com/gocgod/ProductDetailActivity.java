package com.gocgod;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gocgod.adapter.DescriptionTestimonialAdapter;
import com.gocgod.model.ProductData;
import com.gocgod.model.ProductTestimonial;
import com.gocgod.model.ResponseSuccess;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    //@BindView(R.id.pager) carbon.widget.ViewPager pager;
    @BindView(R.id.pager)
    WrapContentViewPager pager;
    @BindView(R.id.tabs)
    TabLayout tabs;
//    @BindView(R.id.expand_text_view)
//    ExpandableTextView comment;

    private FragmentManager fragmentManager;
//    private ProductData data;
    private String deskripsi;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        Bundle bundle = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail Produk");
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
            getProductData(Integer.toString(bundle.getInt("productId")));
            //set object adapter kedalam ViewPager
//            Log.d("lapar", "a");
//            Log.d("maumakan", "lllll");
//            String deskripsi = data.getDescription().replace("\n", "\\n").replace("\r", "\\r");
            //Log.d("lapar", data.toString());

            //pager.setAdapter(new DescriptionTestimonialAdapter(getSupportFragmentManager(),
            //        deskripsi));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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

                ProductData data = result.getSuccess().getData().getProductData();
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
                //
                pager.setAdapter(new DescriptionTestimonialAdapter(getSupportFragmentManager(),deskripsi, productTestimonial, productId));
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {

            }
        });
    }

    @OnClick()
    public void a()
    {

    }
}

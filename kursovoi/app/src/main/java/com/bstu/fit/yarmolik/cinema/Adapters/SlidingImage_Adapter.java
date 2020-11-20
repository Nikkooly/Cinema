package com.bstu.fit.yarmolik.cinema.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bstu.fit.yarmolik.cinema.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

    public class SlidingImage_Adapter extends PagerAdapter {


        private ArrayList<String> IMAGES;
        private ArrayList<String> DESCRIPTION;
        private ArrayList<String> idFilm;
        private LayoutInflater inflater;
        private Context context;
        Fragment currentFragment = null;
        FragmentTransaction ft;


        public SlidingImage_Adapter(Context context,ArrayList<String> IMAGES,ArrayList<String> DESCRIPTION, ArrayList<String> idFilm) {
            this.context = context;
            this.IMAGES=IMAGES;
            this.DESCRIPTION=DESCRIPTION;
            this.idFilm=idFilm;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return IMAGES.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);
            final ViewPager pager=view.findViewById(R.id.pager);
            final TextView textView=imageLayout.findViewById(R.id.filmDescription);
            Picasso.get().load(IMAGES.get(position)).into(imageView);
            imageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //mListener.Open(idFilm.get(position));
                   /* AppCompatActivity activity = (AppCompatActivity) imageLayout.getContext();
                    Fragment myFragment = new InfoFilmFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("idFilmAdapter", idFilm.get(position));
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.sliderFragmentViewPager, myFragment)
                            .addToBackStack(null).commit();*/
                }
            });

            //imageView.setImageResource(IMAGES.get(position));
            textView.setText(DESCRIPTION.get(position));
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }

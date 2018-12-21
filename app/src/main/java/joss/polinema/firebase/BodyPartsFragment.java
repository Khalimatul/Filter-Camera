package joss.polinema.firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Islamiyah Chimpony on 12/17/2018.
 */

public class BodyPartsFragment extends Fragment {
    private List<Integer> mImageIds;
    private Integer mListIndex;
    private  static String TAG = "BodyPartsFragment";
    // variabel TAG
    public static final String IMAGE_ID_LIST = "image_ids";
    public static  final  String LIST_INDEX = "List_index";

    public BodyPartsFragment() { super();
    }
    // variabel global untuk memanggil list gambar heads atau bodies serta method setter nya.

    public void setImageIds(List<Integer>mImageIds){
        this.mImageIds = mImageIds;
    }
    public  void  setmListIndex(Integer mListIndex){
        this.mListIndex = mListIndex;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //t jika “saved instance” tidak ‘null’ maka variabel array dan index memanggal keadaan sebelumnya
        if(savedInstanceState != null){
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }
        //Inisiasi activity tersebut sebagai fragment
        View rootView = inflater.inflate(R.layout.body_part_layout_fragment,container, false);
        //mereferensi id yang ada dilayout fragment
        final ImageView imageView =(ImageView) rootView.findViewById(R.id.img_fragment);
        //ambil gambar untuk ditampilkan
        imageView.setImageResource(mImageIds.get(mListIndex));
//        return super.onCreateView(inflater, container, savedInstanceState);
        if(mImageIds != null){
            imageView.setImageResource(mImageIds.get(mListIndex));
            //merubah index dari gambar yang terdapat pada imageview
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListIndex < mImageIds.size()-1){
                        mListIndex++;
                    }else {
                        mListIndex = 0;
                    }
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });
        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }
        return rootView;
    }
    // menyimpan variabel array beserta index (seperti Session pada pemrograman web)
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putIntegerArrayList(IMAGE_ID_LIST,(ArrayList<Integer>)mImageIds);
        currentState.putInt(LIST_INDEX,mListIndex);
    }
}

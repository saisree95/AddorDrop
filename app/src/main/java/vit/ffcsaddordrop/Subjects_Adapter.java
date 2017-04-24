package vit.ffcsaddordrop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Sai Sree on 9/22/2016.
 */
public class Subjects_Adapter extends BaseAdapter {

    private Context mContext;
    private List<String> mCode;
    private List<String> mTitle;
    private RadioButton yes;

    customButtonListner customListner;

    public interface customButtonListner
    {
        public void onButtonClickListner(int position,int key);
    }

    public void setCustomButtonListner(customButtonListner listner) {
        this.customListner = listner;
    }

    public Subjects_Adapter(List<String> mCode, List<String> mTitle, Context mContext)
    {
        this.mCode = mCode;
        this.mTitle = mTitle;
        this.mContext = mContext;
    }

    public Subjects_Adapter() {
        super();
    }

    @Override
    public int getCount() {
        return mCode.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View list;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            list = new View(mContext);
            list = inflater.inflate(R.layout.single_subject, null);
            TextView coursecode=(TextView)list.findViewById(R.id.ccode);
            TextView coursetitle=(TextView)list.findViewById(R.id.ctitle);
            yes = (RadioButton) list.findViewById(R.id.selectbutton);
            coursecode.setText(mCode.get(position));
            coursetitle.setText(String.valueOf(mTitle.get(position)));

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (customListner != null) {
                        int key = 1;
                        customListner.onButtonClickListner(position, key);
                    }

                }
            });

        }
        else
        {
            list = (View)convertView;
        }

        return list;
    }
}

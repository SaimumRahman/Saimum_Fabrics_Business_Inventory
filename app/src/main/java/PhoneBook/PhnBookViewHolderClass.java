package PhoneBook;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryapplication.R;
import com.example.groceryapplication.itemOnClickListener;

public class PhnBookViewHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView nameContactstxts,numberContactstxts;
    public ImageButton phoncallContactstxts,messageContactstxts;
    public itemOnClickListener Listener;



    public PhnBookViewHolderClass(@NonNull View itemView) {
        super(itemView);

        nameContactstxts=itemView.findViewById(R.id.nameContactstxt);
        numberContactstxts=itemView.findViewById(R.id.numberContactstxt);
        phoncallContactstxts=itemView.findViewById(R.id.phoncallContactstxt);
        messageContactstxts=itemView.findViewById(R.id.messageContactstxt);


    }
    public void setItemOnClickListener(itemOnClickListener itemOnClickListener){
        this.Listener=itemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        Listener.onClick(v,getAdapterPosition(),false);
    }
}

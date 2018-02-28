package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 2/28/2018.
 */

public class ChildInfoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    private final int TEXT = 0, GENDER = 1, PHOTO = 2, BUTTON = 3;

    public interface OnItemClickListener { public void onClick(View view, int position); }

    public class TextInputViewHolder extends RecyclerView.ViewHolder
    {
        private TextInputEditText editTextInput;
        private TextView textViewNotSure;

        public TextInputViewHolder(View itemView) {
            super(itemView);

            editTextInput = (TextInputEditText) itemView.findViewById(R.id.editTextInput);
            textViewNotSure = (TextView) itemView.findViewById(R.id.textViewNotSure);
        }

        public TextInputEditText getEditTextInput() { return editTextInput; }

        public void setEditTextInput(TextInputEditText editTextInput) { this.editTextInput = editTextInput; }

        public TextView getTextViewNotSure() { return textViewNotSure; }

        public void setTextViewNotSure(TextView textViewNotSure) { this.textViewNotSure = textViewNotSure; }

        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public class GenderViewHolder extends RecyclerView.ViewHolder
    {
        private RadioButton radioButtonMale;
        private RadioButton radioButtonFemale;

        public GenderViewHolder(View itemView) {
            super(itemView);

            radioButtonMale = (RadioButton) itemView.findViewById(R.id.radioButtonMale);
            radioButtonFemale = (RadioButton) itemView.findViewById(R.id.radioButtonFemale);
        }

        public RadioButton getRadioButtonMale() { return radioButtonMale; }

        public void setRadioButtonMale(RadioButton radioButtonMale) { this.radioButtonMale = radioButtonMale; }

        public RadioButton getRadioButtonFemale() { return radioButtonFemale; }

        public void setRadioButtonFemale(RadioButton radioButtonFemale) { this.radioButtonFemale = radioButtonFemale; }

        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public class PhotoInputViewHolder extends RecyclerView.ViewHolder
    {
        private CircularImageView imageViewPhoto;
        private TextView textViewUrl;
        private Button buttonBrowse;

        public PhotoInputViewHolder(View itemView) {
            super(itemView);

            //imageViewPhoto = (CircularImageView) itemView.findViewById(R.id.imag)
        }

        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder
    {
        public ButtonViewHolder(View itemView) {
            super(itemView);
        }

        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

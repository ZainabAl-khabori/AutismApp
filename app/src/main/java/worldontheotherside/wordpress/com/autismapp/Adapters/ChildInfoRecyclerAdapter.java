package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Fragments.ChildInfoFragment;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 2/28/2018.
 */

public class ChildInfoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChildInfoFragment.ChildInfoItem> items;
    private OnItemClickListener onItemClickListener;

    private final int TEXT = 0, GENDER = 1, PHOTO = 2, BUTTON = 3;

    public interface OnItemClickListener { public void onClick(View view, int position); }

    public class TextInputViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextInputLayout textInputLayoutTextInput;
        private TextInputEditText editTextInput;
        private TextView textViewNotSure;

        public TextInputViewHolder(View itemView) {
            super(itemView);

            textInputLayoutTextInput = (TextInputLayout) itemView.findViewById(R.id.textInputLayoutTextInput);
            editTextInput = (TextInputEditText) itemView.findViewById(R.id.editTextInput);
            textViewNotSure = (TextView) itemView.findViewById(R.id.textViewNotSure);
        }

        public TextInputEditText getEditTextInput() { return editTextInput; }

        public TextView getTextViewNotSure() { return textViewNotSure; }

        public TextInputLayout getTextInputLayoutTextInput() { return textInputLayoutTextInput; }

        @Override
        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());

            if(view.getId() == textViewNotSure.getId())
            {
                //
            }
        }
    }

    public class GenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private RadioButton radioButtonMale;
        private RadioButton radioButtonFemale;

        public GenderViewHolder(View itemView) {
            super(itemView);

            radioButtonMale = (RadioButton) itemView.findViewById(R.id.radioButtonMale);
            radioButtonFemale = (RadioButton) itemView.findViewById(R.id.radioButtonFemale);
        }

        public RadioButton getRadioButtonMale() { return radioButtonMale; }

        public RadioButton getRadioButtonFemale() { return radioButtonFemale; }

        @Override
        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public class PhotoInputViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private CircularImageView imageViewPhoto;
        private TextView textViewUrl;
        private Button buttonBrowse;

        public PhotoInputViewHolder(View itemView) {
            super(itemView);

            imageViewPhoto = (CircularImageView) itemView.findViewById(R.id.imageViewPhoto);
            textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);
            buttonBrowse = (Button) itemView.findViewById(R.id.buttonBrowse);
        }

        public CircularImageView getImageViewPhoto() { return imageViewPhoto; }

        public TextView getTextViewUrl() { return textViewUrl; }

        public Button getButtonBrowse() { return buttonBrowse; }

        @Override
        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Button buttonCreateAccount;

        public ButtonViewHolder(View itemView) {
            super(itemView);

            buttonCreateAccount = (Button) itemView.findViewById(R.id.buttonCreateAccount);
        }

        public Button getButtonCreateAccount() { return buttonCreateAccount; }

        @Override
        public void onClick(View view)
        {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public ChildInfoRecyclerAdapter(ArrayList<ChildInfoFragment.ChildInfoItem> items) { this.items = items; }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) { return items.get(position).getType(); }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType)
        {
            case TEXT:
                view = inflater.inflate(R.layout.child_info_text_input_view_holder, parent, false);
                return new TextInputViewHolder(view);
            case GENDER:
                view = inflater.inflate(R.layout.child_info_gender_view_holder, parent, false);
                return new GenderViewHolder(view);
            case PHOTO:
                view = inflater.inflate(R.layout.child_info_photo_view_holder, parent, false);
                return new PhotoInputViewHolder(view);
            case BUTTON:
                view = inflater.inflate(R.layout.child_info_button_view_holder, parent, false);
                return new ButtonViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch(holder.getItemViewType())
        {
            case TEXT: TextInputViewHolder textViewHolder = (TextInputViewHolder) holder;
                textViewHolder.getTextInputLayoutTextInput().setHint(items.get(position).getTitle());
                textViewHolder.getEditTextInput().setCompoundDrawablesRelativeWithIntrinsicBounds(items.get(position).getIcon(), 0, 0, 0);
                if(!items.get(position).getTitle().equals(Constants.AUTISM_SPECTRUM_SCORE))
                    textViewHolder.getTextViewNotSure().setVisibility(View.GONE);
                else
                    textViewHolder.getTextViewNotSure().setOnClickListener(textViewHolder);
                break;
            case GENDER: // do nothing actually
                break;
            case PHOTO: PhotoInputViewHolder photoInputViewHolder = (PhotoInputViewHolder) holder;
                photoInputViewHolder.getButtonBrowse().setOnClickListener(photoInputViewHolder);
                break;
            case BUTTON: ButtonViewHolder buttonViewHolder = (ButtonViewHolder) holder;
                buttonViewHolder.getButtonCreateAccount().setOnClickListener(buttonViewHolder);
                break;
        }
    }

    @Override
    public int getItemCount() { return items.size(); }
}

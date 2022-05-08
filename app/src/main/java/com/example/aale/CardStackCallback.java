package com.example.aale;

import androidx.recyclerview.widget.DiffUtil;

import com.example.aale.model.Item;

import java.util.List;

public class CardStackCallback  extends DiffUtil.Callback {

    private List<Item> old, baru;

    public CardStackCallback(List<Item> old, List<Item> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage() == baru.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}
/**
 * Copyright (C) 2016,2017 Verifone, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * VERIFONE, INC. BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Except as contained in this notice, the name of Verifone, Inc. shall not be
 * used in advertising or otherwise to promote the sale, use or other dealings
 * in this Software without prior written authorization from Verifone, Inc.
 */

package com.verifone.swordfish.manualtransaction.SupportFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.verifone.swordfish.manualtransaction.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardAmountEntry.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardAmountEntry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardAmountEntry extends Fragment implements
        NumericKeyboard.OnFragmentInteractionListener,
        ButtonsFragment.OnFragmentInteractionListener {

    private static final String TAG = CardAmountEntry.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private ButtonsFragment buttonsFragment;
    private boolean mCancelable = false;

    public CardAmountEntry() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardAmountEntry.
     */
    public static CardAmountEntry newInstance(String param1, String param2) {
        CardAmountEntry fragment = new CardAmountEntry();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_amount_entry, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        NumericKeyboard numericKeyboard = new NumericKeyboard();
        numericKeyboard.setListener(this);
        buttonsFragment = new ButtonsFragment();
        buttonsFragment.setListener(this);
        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.cardAmountNumericKeyboard, numericKeyboard)
                .add(R.id.cardAmountButtonsCashFrame, buttonsFragment)
                .commit();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onKeyboardButtonPress(String title) {
        mListener.cardKeyboardEntry(title);
    }

    @Override
    public void onButtonPress(int buttonID) {
        switch (buttonID) {
            case 0:
                mListener.cardCancel();
                break;
            case 1:
                mListener.cardBack();
                break;
            case 2:
                mListener.cardCharge();
                break;
            default:
                break;
        }
    }

    @Override
    public void readyToConfigureButtons() {
        buttonsFragment.onConfigureButton(0, mCancelable, getContext().getString(R.string.buttonCancelTx));
        buttonsFragment.onConfigureButton(1, true, getContext().getString(R.string.buttonBack));
        buttonsFragment.onConfigureButton(2, true, getContext().getString(R.string.charge_button_label));

    }

    public void setListener(CardAmountEntry.OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    public void setCancelButton(boolean cancelButton) {
        mCancelable = cancelButton;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void cardKeyboardEntry(String title);

        void cardCancel();

        void cardBack();

        void cardCharge();
    }
}
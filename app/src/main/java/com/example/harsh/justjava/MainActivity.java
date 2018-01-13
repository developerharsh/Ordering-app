package com.example.harsh.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import com.example.harsh.justjava.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    boolean hasWhippedCream;
    boolean haschocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void increment(View view) {
        if(quantity<10){
        quantity=quantity+1;
        display(quantity);
        display1();
    }
    else{
            Toast.makeText(this,"You cannot have more than 10 coffees",Toast.LENGTH_SHORT).show();
        }

    }
    public void decrement(View view) {
        if(quantity>0){
        quantity=quantity-1;
        display(quantity);
        display1();
    }
        else{
            Toast.makeText(this,"You cannot have less than 0 coffees",Toast.LENGTH_SHORT).show();
        }

    }
    public void submitOrder(View view) {
        EditText text=(EditText)findViewById(R.id.name);
        String value=text.getText().toString();
        int price;
        price=calculatePrice();
        String message=createOrderSummary(price,hasWhippedCream,haschocolate,value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject)+" "+value);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    private void display1() {
        int sprice=calculatePrice();
        TextView price = (TextView) findViewById(R.id.price);
        if(hasWhippedCream && haschocolate)
        {
            price.setText("$" + sprice+" ( Whipped Cream: $"+quantity+" Chocolate: $"+(quantity*2)+" )");

        }
        else if(haschocolate){
            price.setText("$" + sprice+" ( Chocolate: $"+(quantity*2)+" )");

        }
        else if(hasWhippedCream )
        {
            price.setText("$" + sprice+" ( Whipped Cream: $"+quantity+" )");

        }
        else{

            price.setText("$" + sprice);

        }


    }

    private int calculatePrice(){
        int price;
        CheckBox whippedCreamCheckbox=(CheckBox)findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream=whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox=(CheckBox)findViewById(R.id.chocolate_checkbox);
        haschocolate=chocolateCheckbox.isChecked();
        if(hasWhippedCream && haschocolate)
        {
            price=quantity*8;

        }
        else if(haschocolate){
            price=quantity*7;

        }
        else if(hasWhippedCream )
        {
             price=quantity*6;

        }
        else{

         price=quantity*5;

    }
        return price;
    }
    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String Name){
       String pricemessage=getString(R.string.order_summaryname,Name);
        pricemessage+="\n"+getString(R.string.order_summarywhippedcream,addWhippedCream);
        pricemessage+="\n"+getString(R.string.order_summaryChocolate,addChocolate);
        pricemessage=pricemessage+"\n"+getString(R.string.order_summaryQuantity,quantity);
        pricemessage=pricemessage+"\n"+getString(R.string.order_summaryprice,price);
        pricemessage=pricemessage+ "\n"+ getString(R.string.thank_you);
        return pricemessage;
    }
}

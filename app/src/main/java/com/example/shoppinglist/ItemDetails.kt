package com.example.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppinglist.data.Item
import com.example.shoppinglist.databinding.ItemDetailsBinding
import com.example.shoppinglist.retrofitConversion.MoneyAPI
import com.example.shoppinglist.retrofitConversion.MoneyResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemDetails : AppCompatActivity() {
    private lateinit var  binding: ItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var item: Item = intent.getSerializableExtra("itemId") as Item
        var itemPrice: Int = item.estimatedPrice

        binding.tvNameDetail.setText(item.name)
        binding.tvDescriptionDetail.setText(item.description)

        //Find the category name
        val categories = resources.getStringArray(R.array.categories).toList()
        binding.tvCategoryDetail.setText(categories.get(item.category).toString())

        //Status of the item
        if(item.status)
            binding.tvStatusDetail.setText("Purchased")
        else
            binding.tvStatusDetail.setText("Not purchased yet")

        binding.tvPriceDetail.setText(itemPrice.toString()+" HUF")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.frankfurter.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val currencyAPI = retrofit.create(MoneyAPI::class.java)

        binding.btnGetRates.setOnClickListener {
            //To prevent infinite appending
            binding.tvResult.setText("")
            binding.tvPriceDetail.setText(itemPrice.toString()+" HUF")

            val rateCall = currencyAPI.getRates("HUF")

            rateCall.enqueue(object : Callback<MoneyResult> {
                override fun onFailure(call: Call<MoneyResult>, t: Throwable) {
                    binding.tvResult.setText(t.message)
                }

                override fun onResponse(call: Call<MoneyResult>, response: Response<MoneyResult>) {
                    //HUF to EUR
                    var eurRate: Double? = response.body()?.rates?.EUR?.toDouble()
                    var priceInEUR = String.format("%.3f", itemPrice * eurRate!! )

                    binding.tvPriceDetail.append("\n"+priceInEUR+" EUR")
                    binding.tvResult.append("EUR: "+eurRate+"\n")

                    //HUF to CAD
                    var cadRate: Double? = response.body()?.rates?.CAD?.toDouble()
                    var priceInCAD = String.format("%.3f", itemPrice * cadRate!! )

                    binding.tvPriceDetail.append("\n"+priceInCAD+" CAD")
                    binding.tvResult.append("CAD: "+cadRate+"\n")

                    //HUF to JPY
                    var jpyRate: Double? = response.body()?.rates?.JPY?.toDouble()
                    var priceInJPY = String.format("%.3f", itemPrice * jpyRate!! )

                    binding.tvPriceDetail.append("\n"+priceInJPY+" JPY")
                    binding.tvResult.append("JPY: "+jpyRate+"\n")
                }
            })
        }
    }
}
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canerdedeoglu.afinal.R
import com.canerdedeoglu.afinal.models.Order
import com.canerdedeoglu.afinal.models.Product

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdTextView: TextView = itemView.findViewById(R.id.txtOrderTitle)
        private val totalQuantityTextView: TextView = itemView.findViewById(R.id.txtTotalQuantity)
        private val totalPriceTextView: TextView = itemView.findViewById(R.id.txtTotalPrice)
        private val productsRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewProducts)

        fun bind(order: Order) {
            orderIdTextView.text = "Sipariş No: ${order.id}"
            totalQuantityTextView.text = "Toplam Adet: ${order.totalQuantity}"
            totalPriceTextView.text = "Toplam Tutar: ${order.discountedTotal} TL"

            // Products RecyclerView setup
            productsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            productsRecyclerView.adapter = ProductAdapter(order.products)
        }
    }

    class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
            return ProductViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = products[position]
            holder.bind(product)
        }

        override fun getItemCount(): Int {
            return products.size
        }

        class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val productImageView: ImageView = itemView.findViewById(R.id.productImage)
            private val productTitleTextView: TextView = itemView.findViewById(R.id.productTitle)
            private val productPriceTextView: TextView = itemView.findViewById(R.id.productPrice)

            fun bind(product: Product) {
                Glide.with(itemView.context)
                    .load(product.thumbnail)
                    .into(productImageView)
                productTitleTextView.text = product.title
                productPriceTextView.text = "${product.price} TL"
            }
        }
    }
}

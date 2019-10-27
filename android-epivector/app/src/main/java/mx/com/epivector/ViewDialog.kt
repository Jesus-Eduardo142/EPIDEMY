package mx.com.epivector

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget

class ViewDialog(activity:Activity) {
    internal var activity: Activity
    internal lateinit var dialog: Dialog
    lateinit var  animationDrawable: AnimationDrawable
    lateinit var  mProgressBar: ImageView

    init{
        this.activity = activity
    }


    fun showDialog() {
        dialog = Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.parseColor("#CAFFFFFF")))//ColorDrawable(Color.WHITE))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_loading)
        val gifImageView = dialog.findViewById<ImageView>(R.id.custom_loading_imageView)
        val imageViewTarget = GlideDrawableImageViewTarget(gifImageView)
        Glide.with(activity)
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget)
        dialog.show()

        // Glide.with(thiscontext).asGif().load(R.raw.badge_green_yellow).into(holder.ivHourlyIcon);

    }



    fun hideDialog() {
        dialog.dismiss()
    }
}

package com.example.identify

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.base.BaseActivity
import com.example.utils.setTextViewText
import com.example.utils.setViewVisibility

class BasicClassActivity : BaseActivity() {

    private var wasteType = RECYCLABLE
    private var tvTitle: TextView? = null
    private var ivBack: ImageView? = null
    private var tvGarbageTitle: TextView? = null
    private var tvGarbageSubTitle: TextView? = null
    private var ivIcon: ImageView? = null
    private var tvDefineDesc: TextView? = null
    private var tvNavDesc: TextView? = null
    private var clTitle: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_class)
        wasteType = intent.getStringExtra(WASTE_TYPE) ?: RECYCLABLE
        initView()
    }

    private fun initView() {
        tvGarbageTitle = findViewById(R.id.tv_garbage_title)
        tvGarbageSubTitle = findViewById(R.id.tv_garbage_subtitle)
        ivIcon = findViewById(R.id.iv_icon)
        tvDefineDesc = findViewById(R.id.tv_define_desc)
        tvNavDesc = findViewById(R.id.tv_nav_desc)
        clTitle = findViewById(R.id.cl_title)
        tvTitle = findViewById(R.id.tv_title)
        ivBack = findViewById(R.id.iv_back)
        ivBack.setViewVisibility(View.VISIBLE)

        when (wasteType) {
            RECYCLABLE -> {
                clTitle?.setBackgroundColor(resources.getColor(R.color.blue_recyclable))
                tvGarbageTitle?.text = RECYCLABLE_TITLE
                tvGarbageSubTitle?.text = RECYCLABLE_SUB_TITLE
                tvDefineDesc?.text = RECYCLABLE_DEFINE
                tvNavDesc?.text = RECYCLABLE_NAV
                ivIcon?.setImageResource(R.drawable.icon_recycleable)
                tvTitle.setTextViewText(RECYCLABLE_TITLE)
            }
            HARMFUL -> {
                clTitle?.setBackgroundColor(resources.getColor(R.color.red_harmful))
                tvGarbageTitle?.text = HARMFUL_TITLE
                tvGarbageSubTitle?.text = HARMFUL_SUB_TITLE
                tvDefineDesc?.text = HARMFUL_DEFINE
                tvNavDesc?.text = HARMFUL_NAV
                ivIcon?.setImageResource(R.drawable.icon_harmful)
                tvTitle.setTextViewText(HARMFUL_TITLE)
            }
            KITCHEN -> {
                clTitle?.setBackgroundColor(resources.getColor(R.color.green_kitchen))
                tvGarbageTitle?.text = KITCHEN_TITLE
                tvGarbageSubTitle?.text = KITCHEN_SUB_TITLE
                tvDefineDesc?.text = KITCHEN_DEFINE
                tvNavDesc?.text = KITCHEN_NAV
                ivIcon?.setImageResource(R.drawable.icon_kitchen)
                tvTitle.setTextViewText(KITCHEN_TITLE)
            }
            RESIDUAL -> {
                clTitle?.setBackgroundColor(resources.getColor(R.color.black_residual))
                tvGarbageTitle?.text = RESIDUAL_TITLE
                tvGarbageSubTitle?.text = RESIDUAL_SUB_TITLE
                tvDefineDesc?.text = RESIDUAL_DEFINE
                tvNavDesc?.text = RESIDUAL_NAV
                ivIcon?.setImageResource(R.drawable.icon_residual)
                tvTitle.setTextViewText(RESIDUAL_TITLE)
            }
        }
    }

    companion object {
        const val WASTE_TYPE = "wasteType"

        const val RECYCLABLE = "recyclable"
        const val RECYCLABLE_TITLE = "可回收垃圾"
        const val RECYCLABLE_SUB_TITLE = "RECYCLABLE WASTE"
        const val HARMFUL = "harmful"
        const val HARMFUL_TITLE = "有害垃圾"
        const val HARMFUL_SUB_TITLE = "HARMFUL WASTE"
        const val KITCHEN = "kitchen"
        const val KITCHEN_TITLE = "厨余垃圾"
        const val KITCHEN_SUB_TITLE = "KITCHEN WASTE"
        const val RESIDUAL = "residual"
        const val RESIDUAL_TITLE = "其他垃圾"
        const val RESIDUAL_SUB_TITLE = "RESIDUAL WASTE"

        const val RECYCLABLE_DEFINE = "可回收物指适宜回收利用和资源化利用的生活废弃物 。可回收物主要品种包括：废纸、废弃塑料瓶、废金属、废包装物、废旧纺织物、废弃电器电子产品、废玻璃、废纸塑铝复合包装等。"

        const val HARMFUL_DEFINE = "有害垃圾指对人体健康或者自然环境造成直接或者潜在危害的生活 废弃物 。 常见的有害垃圾包括废灯管、废油漆、杀虫剂、废弃化妆品、过期药品、 废电池 、废灯泡、废 水银温度计 等，有害垃圾需按照特殊正确的方法安全处理。"

        const val KITCHEN_DEFINE = " 厨余垃圾是指居民日常生活及食品加工、饮食服务、单位供餐等活动中产生的垃圾，包括丢弃不用的菜叶、剩菜、剩饭、果皮、蛋壳、茶渣、骨头等，其主要来源为家庭厨房、餐厅、饭店、食堂、市场及其他与食品加工有关的行业。"

        const val RESIDUAL_DEFINE = "其他垃圾指危害比较小，没有再次利用的价值的垃圾，如建筑垃圾，生活垃圾等，一般都采取填埋、焚烧、卫生分解等方法处理，部分还可以使用生物分解的方法解决，如放蚯蚓等。其他垃圾是可回收物、厨余垃圾、有害垃圾剩余下来的一种垃圾种类。\n其他垃圾包括砖瓦陶瓷、渣土、卫生间废纸、瓷器碎片、动物排泄物、一次性用品等难以回收的废弃物，采取卫生填埋可有效减少对地下水、地表水、土壤及空气的污染。到目前为止，人类暂时还没有有效化解其他垃圾的好方法，所以要尽量少产生。"

        const val RECYCLABLE_NAV = "● 纸类垃圾投放时宜折好压平\n" +
                "● 金属尖利器物宜包装牢固,易拉罐盒类应压扁○纺织类垃圾投放应洗净并折好压平\n" +
                "● 玻璃类垃圾投放撕掉标签，碎玻璃应包装牢固\n"

        const val HARMFUL_NAV = "● 投放时请注意轻放\n" +
                "● 有害垃圾投放宜保持物品的完整性\n" +
                "● 电池应采取防止有害物质外漏的措施\n" +
                "● 如易挥发请密封后投放\n" +
                "● 易破损的请连带包装或包裹后轻放\n"

        const val KITCHEN_NAV = "● 流质的食物垃圾，如牛奶等应直接倒入下水口\n" +
                "● 有包装的应当将包装物去除后投放\n" +
                "● 厨房产生的,如菜叶菜帮、剩饭剩菜、植物应投放至此分类\n"

        const val RESIDUAL_NAV = "● 尽量沥干水分\n" +
                "● 难以辨识类别的生活垃圾投入其他垃圾容器内\n"
    }
}
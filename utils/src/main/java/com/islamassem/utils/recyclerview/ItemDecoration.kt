package com.islamassem.utils.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islamassem.utils.inflate

class ItemDecoration (private val headerAdapter: HeaderAdapter<*,*>) : RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }
    var mStickyHeaderHeight = 0
    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }
        val headerPos = headerAdapter.getHeaderPositionForItem(topChildPosition)
        val currentHeader = inflate(layoutResourceId = headerAdapter.getHeaderLayout(headerPos), parent = parent)
        headerAdapter.bindHeaderData(currentHeader,headerPos)
        fixLayoutSize(parent, currentHeader)

        val contactPoint = currentHeader.bottom
        val childInContact = getChildInContact(parent, contactPoint, headerPos)

        if (childInContact != null && headerAdapter.isHeader(
                parent.getChildAdapterPosition(
                    childInContact
                )
            )) {
            canvas.save()
            canvas.translate(0f, childInContact.top.toFloat() - currentHeader.height)
            currentHeader.draw(canvas)
            canvas.restore()
            return
        }

        //Draw Header/
        canvas.save()
        canvas.translate(0f, 0f)
        currentHeader.draw(canvas)
        canvas.restore()
        canvas.save()
//        lateinit var  rect : Rect
//        parent.getDrawingRect(rect)
//        canvas.drawLine(rect.left,rect.right)
//        canvas.restore()
    }
    private fun getChildInContact(
        parent: RecyclerView,
        contactPoint: Int,
        currentHeaderPos: Int
    ): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            var heightTolerance = 0
            val child: View = parent.getChildAt(i)

            //measure height tolerance with child if child is another header
            if (currentHeaderPos != i) {
                val isChildHeader: Boolean =
                    headerAdapter.isHeader(parent.getChildAdapterPosition(child))
                if (isChildHeader) {
                    heightTolerance = mStickyHeaderHeight - child.height
                }
            }

            //add heightTolerance if child top be in display area
            var childBottomPosition: Int
            childBottomPosition = if (child.top > 0) {
                child.bottom + heightTolerance
            } else {
                child.bottom
            }
            if (childBottomPosition > contactPoint) {
                if (child.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }


    /**
     * Properly measures and layouts the top sticky header.
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec: Int =
            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec: Int =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight.also {
            mStickyHeaderHeight = it
        })

    }
}
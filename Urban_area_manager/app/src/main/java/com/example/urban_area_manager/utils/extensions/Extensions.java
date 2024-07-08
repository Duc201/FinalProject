package com.example.urban_area_manager.utils.extensions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.StepService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Extensions {


    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void hide(View view) {
        view.setVisibility(View.GONE);
    }

    public static Drawable getDrawableCompat(Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }

    public static int getColorCompat(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }
    public static int calculateServiceUsageDays(Date dateCreated, Date dateDeleted) {
        Calendar calCreate = Calendar.getInstance();
        calCreate.setTime(dateCreated);

        Calendar calDelete = Calendar.getInstance();
        calDelete.setTime(dateDeleted);

        // Lấy ngày đầu tiên của tháng của dateDelete
        Calendar calFirstDayOfMonthDelete = Calendar.getInstance();
        calFirstDayOfMonthDelete.setTime(dateDeleted);
        calFirstDayOfMonthDelete.set(Calendar.DAY_OF_MONTH, 1);

        // Kiểm tra nếu cùng tháng và cùng năm
        if (calCreate.get(Calendar.YEAR) == calDelete.get(Calendar.YEAR) &&
                calCreate.get(Calendar.MONTH) == calDelete.get(Calendar.MONTH)) {
            return daysBetween(calCreate, calDelete);
        } else {
            return daysBetween(calFirstDayOfMonthDelete, calDelete);
        }
    }

    // Phương thức tính số ngày giữa hai ngày
    private static int daysBetween(Calendar startCal, Calendar endCal) {
        Calendar start = (Calendar) startCal.clone();
        Calendar end = (Calendar) endCal.clone();
        int daysCount = 0;
        while (start.before(end)) {
            start.add(Calendar.DAY_OF_MONTH, 1);
            daysCount++;
        }
        return daysCount;
    }
    public static StringBuilder formatMoney(long money){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(money).append(" VNĐ");
    }
    public static StringBuilder formatMoney(double money){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(money).append(" VNĐ");
    }
    public static StringBuilder formatMoney(int money){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(money).append(" VNĐ");
    }
    public static long calculateElectricBill(List<Step> steps, long consumption) {
        int totalCost = 0;
        long remainingQuantity = consumption;

        for (Step step : steps) {
            if (remainingQuantity <= 0) break;
            int stepRange = step.getEndValue() - step.getStartValue() + 1;
            long consumedInStep = Math.min(remainingQuantity, stepRange);

            totalCost += consumedInStep * step.getPrice();
            remainingQuantity -= consumedInStep;
        }

        return (totalCost*110)/100;
    }
    public static long calculateWaterBill(StepService water, long consumption) {
        long total = 0;
        int[] limits = {10, 20, 30, 40}; // Giới hạn cho từng bậc
        long[] prices = {1, 2, 1,1};


        for (int i = 0; i < limits.length; i++) {
            if (consumption > limits[i]) {
                total += (limits[i] - (i == 0 ? 0 : limits[i-1])) * prices[i];
            } else {
                total += (consumption - (i == 0 ? 0 : limits[i-1])) * prices[i];
                return total; // Tiêu thụ nằm trong giới hạn của bậc hiện tại, kết thúc tính toán
            }
        }

        // Nếu tiêu thụ vượt quá bậc cao nhất
        if (consumption > limits[limits.length - 1]) {
            total += (consumption - limits[limits.length - 1]) * prices[prices.length - 1];
        }
        total = (total*110)/100;
        return total;
    }

    public static String FormatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
            Bitmap bitmap = null;

            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }

            // Lấy kích thước của Drawable
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            // Xử lý kích thước không hợp lệ
            if (width <= 0 || height <= 0) {
                width = 1;
                height = 1;
            }

            // Tạo Bitmap với nền màu trắng
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE); // Vẽ màu trắng lên toàn bộ Bitmap

            // Vẽ Drawable lên Bitmap
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        }



}

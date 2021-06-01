package com.islamassem.utils

/*
*
*
    public static String formatDate(Date date){
        return getDateOnlyFormatter().format(date);
    }
    public static SimpleDateFormat getDateOnlyFormatter() {
        return getDateOnlyFormatter == null
                ?getDateOnlyFormatter =new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                :getDateOnlyFormatter;
    }
    public static String formatDateApi(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date);
    }
    public static SimpleDateFormat getDateFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ENGLISH);
    }
//    public static String formatDate(Date date){
//        return getDateOnlyFormatter().format(date)+getDayOfMonthSuffix(date.getDate());
//    }
    private static SimpleDateFormat getDateOnlyFormatter;
//    public static SimpleDateFormat getDateOnlyFormatter() {
//        return getDateOnlyFormatter == null
//                ?getDateOnlyFormatter =new SimpleDateFormat("MMM d", Locale.ENGLISH)
//                :getDateOnlyFormatter;
//    }
    @Nullable
    public static Date format(String date){
        try{
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }catch (Exception e){
            return null;}
    }
    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
    public static SimpleDateFormat getDateFormatterSeconds() {
        return new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
    }
    public static List<Date> getDaysBetweenDates(Date startdate,int count)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        for (int i =0;i<count;i++)
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }
* */
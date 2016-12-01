//package com.kabirkang.filmbrowser.data;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.net.Uri;
//
///**
// * Created by kabirkang on 11/27/16.
// */
//
//public class FilmProvider extends ContentProvider {
//    private static final UriMatcher sUriMatcher = buildUriMatcher();
//    private FilmDbHelper mOpenHelper;
//    static final int FILM = 100;
//
//    static UriMatcher buildUriMatcher() {
//
//        // All paths added to the UriMatcher have a corresponding code to return when a match is
//        // found.  The code passed into the constructor represents the code to return for the root
//        // URI.  It's common to use NO_MATCH as the code for this case.
//        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
//        final String authority = FilmContract.CONTENT_AUTHORITY;
//
//        // For each type of URI you want to add, create a corresponding code.
//        matcher.addURI(authority, FilmContract.PATH_FILM, FILM);
////        matcher.addURI(authority, FilmContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
////        matcher.addURI(authority, FilmContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);
//
////        matcher.addURI(authority, FilmContract.PATH_LOCATION, LOCATION);
//        return matcher;
//    }
//
//    @Override
//    public boolean onCreate() {
//        mOpenHelper = new FilmDbHelper(getContext());
//        return true;
//    }
//
//    @Override
//    public String getType(Uri uri) {
//
//        // Use the Uri Matcher to determine what kind of URI this is.
//        final int match = sUriMatcher.match(uri);
//
//        switch (match) {
//            // Student: Uncomment and fill out these two casesß
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//    }
//
//    @Override
//    public int update(
//            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        return 0;
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        return uri;
//    }
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return 0;
//    }
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
//                        String sortOrder) {
//        // Here's the switch statement that, given a URI, will determine what kind of request it is,
//        // and query the database accordingly.
//        Cursor retCursor;
//        switch (sUriMatcher.match(uri)) {
//            // "weather/*/*"
//            case FILM:
//            {
//                retCursor = getWeatherByLocationSettingAndDate(uri, projection, sortOrder);
//                break;
//            }
//
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
//        return retCursor;
//    }
//}

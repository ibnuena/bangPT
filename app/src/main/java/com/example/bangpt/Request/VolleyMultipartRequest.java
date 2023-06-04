package com.example.bangpt.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyMultipartRequest extends Request<NetworkResponse> {
    private final String boundary = "volleyMultipartBoundary";
    private final String lineEnd = "\r\n";
    private final String twoHyphens = "--";

    private Response.Listener<NetworkResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private Map<String, String> mHeaders;

    public VolleyMultipartRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.mHeaders = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    public void setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        try {
            for (String key : getParams().keySet()) {
                buildTextPart(dataOutputStream, key, getParams().get(key));
            }

            if (getByteData() != null) {
                for (String key : getByteData().keySet()) {
                    buildDataPart(dataOutputStream, key, getByteData().get(key));
                }
            }

            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return new HashMap<>();
    }

    protected Map<String, DataPart> getByteData() throws AuthFailureError {
        return new HashMap<>();
    }

    private void buildTextPart(DataOutputStream dataOutputStream, String key, String value) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.writeBytes(value + lineEnd);
    }

    private void buildDataPart(DataOutputStream dataOutputStream, String key, DataPart dataPart) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + dataPart.getFileName() + "\"" + lineEnd);
        if (dataPart.getType() != null && !dataPart.getType().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + dataPart.getType() + lineEnd);
        }
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.write(dataPart.getData());
        dataOutputStream.writeBytes(lineEnd);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

    public static class DataPart {
        private String fileName;
        private byte[] data;
        private String type;

        public DataPart(String fileName, byte[] data) {
            this.fileName = fileName;
            this.data = data;
        }

        public DataPart(String fileName, byte[] data, String type) {
            this.fileName = fileName;
            this.data = data;
            this.type = type;
        }

        public String getFileName() {
            return fileName;
        }

        public byte[] getData() {
            return data;
        }

        public String getType() {
            return type;
        }
    }
}


package com.chiyuanye.entity;

import java.io.OutputStream;

public class DownloadResult extends BaseResult{
	private OutputStream outputStream;
	public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
	public OutputStream getOutputStream() {
        return outputStream;
    }
}

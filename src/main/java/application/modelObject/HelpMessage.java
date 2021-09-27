package application.modelObject;

import java.io.Serializable;

public class HelpMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7911133264141526777L;
	private String text;
	private boolean sender;// server=0 , client=1
	private String data;
	
	public HelpMessage(String text, boolean sender, String data) {
		super();
		this.text = text;
		this.sender = sender;
		this.data = data;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSender() {
		return sender;
	}

	public void setSender(boolean sender) {
		this.sender = sender;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + (sender ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HelpMessage other = (HelpMessage) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (sender != other.sender)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s;
		if(sender)
			s="Server";
		else
			s="Client";
		return "HelpMessage [text=" + text + ", sender=" + s + ", data=" + data + "]";
	}
	
	
	
}

package edu.wpi.easyrent;

import edu.wpi.easyrent.security.SecurityContext;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
	private Button signinBtn;
	private EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		signinBtn = (Button) this.findViewById(R.id.signinBtn);
		signinBtn.setOnClickListener(this);
		username = (EditText) this.findViewById(R.id.username);
		password = (EditText) this.findViewById(R.id.password);
	}

	@SuppressLint("NewApi")
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.signinBtn) {
			String username = this.username.getText().toString();
			String password = this.password.getText().toString();
			if (username != null && !username.isEmpty() && password != null & !password.isEmpty()) {
				if ((username.equals("tenant") || username.equals("landlord")) && password.equals("123")) {
					this.username.setText("");
					this.password.setText("");
					this.username.requestFocus();
					SecurityContext ctx = SecurityContext.getInstance();
					ctx.setUser(username);
					Intent intent = new Intent(this, HomeActivity.class);
					this.startActivity(intent);
				} else {
					this.username.setText("");
					this.password.setText("");
					this.username.requestFocus();
					Toast.makeText(getBaseContext(), "Username or password incorrect. Try tenant/123 or landlord/123", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}

package application.net.stuff;

public class Protocol {
	
	public final static String LOGIN="Login";
	public final static String REGISTRATION="Registration";
	public final static String HELP_MESSAGES=" send message";
	public final static String GET_HELP_MESSAGES="get  message";

	
	public final static String SUCCESS="done";
	public final static String ERROR="Error during connection";
	public final static String AUTHENTICATION_NO_VALID_ERROR="Username or password are invalid";
	public final static String USER_LOGGED_ERROR="Error user already logged in";
	public static final String USER_DISCONNECTED = "User  offline";

	public static final String SERVER_ERROR = "A server error happened";
	public static final String SERVER_ERROR_DB = "There was a server error! ";

	public static final String REQUEST_ERROR = "request's Error";

	public final static String REGISTRATION_EMAIL_ERROR="Error email is invalid";
	public final static String REGISTRATION_EXISTS_ERROR="Error email exists ";
	
	public final static String REGISTRATION_PASSWORD_NOTVALID1="Password must contains at least once  lowercase letter";
	public final static String REGISTRATION_PASSWORD_NOTVALID2="Password must contains at least once  uppercase letter";
	public final static String REGISTRATION_PASSWORD_NOTVALID3="Password not valid";


	
	public final static String INSERT_CLIENTE="Cliente";
	public final static String INSERT_PRODOTTO="Prodotto";
	public final static String INSERT_FORNITORE="Fornitore";
	public final static String INSERT_FATTURA="Fattura";
	
	public final static String CLIENTE_SUCCESS="Cliente inserito con successo!";
	public final static String CLIENTE_DELITE="Cliente elimina";
	public final static String CLIENTE_EDIT="Cliente modifica";
	public final static String CLIENTE_SEARCH="cliente cerca";
	public final static String CLIENTE_DELITE_SUCCESS="Cliente eliminato con successo!";
	public final static String CLIENTE_EDIT_SUCCESS="Cliente modificato con successo!";
	public static final String DELETE_CLIENTE = "elimina cliente";
	public static final String CLIENTE_DELETE_ERROR = "Non è stato possibile eliminare il cliente.Riprova!";
	public static final String CLIENTE_EXISTS = "Attenzione il Cliente esiste già!";

	

	public final static String FORNITORE_SUCCESS="Fornitore inserito con successo!";
	public final static String FORNITORE_EDIT="fornitore modifica";
	public final static String FORNITORE_DELITE="Fornitore elimina";
	public final static String FORNITORE_SEARCH="Fornitore cerca";
	public final static String FORNITORE_DELITE_SUCCESS="Fornitore eliminato con successo!";
	public final static String FORNITORE_EDIT_SUCCESS="Fornitore modificato con successo!";
	public final static String FORNITORE_DELITE_ERROR="Non è stato possibile eliminare il fornitore.Riprova!";
	public static final String FORNITORE_EXISTS = "Attenzione il Fornitore esiste!";



	public final static String CLIENTE_ERROR="Si è verificato un erorre";
	public final static String FORNITORE_ERROR="Non è stato possibile aggiungere il fornitore";
	
	public final static String GET_ALL_CLIENTI = "Dammi tutti clienti";
	public final static String GET_ALL_FORNITORI = "Dammi tutti i fornitori";
	public final static String PDF_CLIENTE = "pdf cliente";
	public final static String PDF_FORNITORE = "pdf fornitore";

	public final static String PRODOTTO_SUCCESS="Prodotto inserito con successo!";
	public final static String PRODOTTO_ERROR="Non è stato possibile aggiungere il prodotto! Riprova più tardi!";
	public static final String GET_ALL_PRODOTTI = "Dammi tutti i prodotti";
	public static final String GET_IMG_PRODOTTO = "Dammi  immagine prodotto";

	public static final String GET_EMAIL_USER = "Dammi  email cliente";
	public static final String SET_PASSWORD_USER = "edit  password cliente";

	
	public static final String GET_NUM_PRODOTTI = "Dammi  il numero di prodotti";
	public static final String GET_NUM_CLIENTI = "Dammi  il numero di clienti";
	public static final String GET_NUM_FORNITORI = "Dammi il numero di fornitori ";
	public static final String UPDATE_PRODOTTO = "udpate prod ";

	public static final String ADD_TERRITORIO = "Aggiungi territorio";
	public static final String EDIT_TERRITORIO = "Modifica territorio";
	public static final String GET_TERRITORI  = "Dammi  i territori";
	public static final String ASSEGNA_TERRITORIO = "assegna territorio";
	public static final String CONSEGNA_TERRITORIO = "consegna territorio";
	public static final String GET_ASSEGNAMENTO_TERRITORIO  = "Dammi  info territorio";

	public static final String SUCCESS_TERRITORIO = "Territorio aggiunto con successo!";

	public static final String ADD_EVENTO = "Aggiungi evento!";
	public static final String GET_EVENTI = "ottieni eventi!";
	public static final String DELETE_EVENTO = "Elimina evento!";
	public static final String EXIST_EVENTO = "Attenzione, evento già esistente!";

	
	public static final String UPDATE_CONT = "update cont!";




}

namespace UserService.Libs.Bcrypt
{
    public class BcryptEncoder
    {
        public static string EncodePassword(string password)
        {
            try
            {

                if (String.IsNullOrEmpty(password)) {
                    throw new ArgumentNullException("empty password");
                }

                byte[] encData_byte = new byte[password.Length];
                encData_byte = System.Text.Encoding.UTF8.GetBytes(password);
                string encodedData = Convert.ToBase64String(encData_byte);
                return encodedData;
            } catch (Exception ex) {
                throw new Exception("Error in base64Encode" + ex.Message);
            }
        }
    }
}

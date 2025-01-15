using System.Security.Cryptography;
using System.Text;

namespace UserService.Libs.Bcrypt
{
    public class BcryptEncoder
    {
        private const int SaltSize = 16; // 128-bit
        private const int HashSize = 32; // 256-bit
        private const int Iterations = 10000;

        public static string EncodePassword(string password)
        {
            var rng = RandomNumberGenerator.Create();

            byte[] salt = new byte[SaltSize];

            rng.GetBytes(salt);

            byte[] hash = hashPasswordWithSalt(password, salt);

            byte[] hashBytes = new byte[SaltSize + HashSize];
            Array.Copy(salt, 0, hashBytes, 0, SaltSize);
            Array.Copy(hash, 0, hashBytes, SaltSize, HashSize);

            return Convert.ToBase64String(hashBytes);
        }

        /// <summary>
        /// Compares a plain-text password with a previously hashed password.
        /// </summary>
        /// <param name="password">The plain-text password to check.</param>
        /// <param name="encodedPassword">The encoded password to compare against.</param>
        /// <returns>True if the password matches; otherwise, false.</returns>
        public static bool ComparePassword(string password, string encodedPassword)
        {
            // Decode the stored password
            byte[] hashBytes = Convert.FromBase64String(encodedPassword);

            // Extract the salt
            byte[] salt = new byte[SaltSize];
            Array.Copy(hashBytes, 0, salt, 0, SaltSize);

            // Hash the input password with the extracted salt
            byte[] hash = hashPasswordWithSalt(password, salt);

            // Extract the stored hash
            for (int i = 0; i < HashSize; i++)
            {
                if (hashBytes[SaltSize + i] != hash[i])
                    return false;
            }

            return true;
        }

        private static byte[] hashPasswordWithSalt(string password, byte[] salt)
        {
            var pbkdf2 = new Rfc2898DeriveBytes(password, salt, Iterations, HashAlgorithmName.SHA256);
            return pbkdf2.GetBytes(HashSize);
        }

    }
}

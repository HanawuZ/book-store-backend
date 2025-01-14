namespace UserService.Libs.Http
{
    public class HttpServe<T>
    {
        public int Status { get; set; }
        public string Message { get; set; }
        public T? Data { get; set; }

        public HttpServe(int status, string message, T data)
        {
            Status = status;
            Message = message;
            Data = data;
        }
    }
}

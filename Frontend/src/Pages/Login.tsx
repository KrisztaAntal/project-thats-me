import LoginForm from "../Components/LoginForm.tsx";
import loginImage from "../assets/login_background.svg";

function Login() {
    return (
        <div className="flex items-center justify-center h-screen">
            <div
                className="flex items-start justify-center bg-teal-700 h-screen w-screen sm:w-3/4 md:w-2/3 lg:w-3/4 lg:items-center lg:h-[85vh] lg:rounded-2xl lg:drop-shadow-xl ">
                <div className="w-full p-8 flex flex-col my-1 sm:px-16 lg:w-1/2 lg:max-h-[85vh]">
                    <div className="text-white text-4xl font-black mb-8 lg:mb-12">Logo</div>
                    <div className=" overflow-auto">
                        <LoginForm/>
                    </div>
                </div>
                <div className="w-1/2 rounded-r-2xl hidden lg:flex justify-center">
                    <img className="w-screen h-[85vh] object-cover object-top rounded-r-2xl" src={loginImage}
                         alt="geometric_shape"/>
                </div>
            </div>
        </div>
    )
}

export default Login;
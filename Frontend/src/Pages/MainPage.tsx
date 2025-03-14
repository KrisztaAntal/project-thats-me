import {useAuth} from "../authProvider/useAuth.tsx";
import {useNavigate} from "react-router-dom";

function MainPage() {
    const {logout} = useAuth()
    const navigate = useNavigate();

    function handleLogout() {
        logout();
        navigate("/login");
    }

    return (<div>
        <button onClick={handleLogout}>logout</button>
        <h1>Welcome!</h1>
    </div>)
}

export default MainPage;
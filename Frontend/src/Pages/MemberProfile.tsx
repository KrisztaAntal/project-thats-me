import {useAuth} from "../authProvider/useAuth.tsx";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import MemberProfileHeader from "../Components/MemberProfile/MemberProfileHeader.tsx";
import MemberProfileBasicDataList from "../Components/MemberProfile/MemberProfileBasicDataList.tsx";

async function getMemberDataByUsername(username, token) {
    try {
        const response = await fetch(`/api/member/${username}`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            }
        );
        return await response.json();
    } catch (error) {
        console.error("Error fetching user:", error);
    }

}


function MemberProfile() {
    const {member, token} = useAuth();
    const [memberByUsername, setMemberByUsername] = useState(null);
    const {username} = useParams();

    useEffect(() => {
        if (!username) return;

        async function fetchMemberByUsername() {
            const memberInPath = await getMemberDataByUsername(username, token);
            setMemberByUsername(memberInPath);
        }

        fetchMemberByUsername();
    }, [token, username])


    return (
        <div className={"flex justify-center items-center h-screen"}>
            <div className={"flex flex-col items-center lg:w-3/4 lg:drop-shadow-xl lg:justify-center lg:h-[90vh]"}>
                <MemberProfileHeader member={memberByUsername} token={token}/>
                <div className={" lg:flex lg:w-full lg:h-full"}>

                    <MemberProfileBasicDataList member={member} memberByUsername={memberByUsername}/>
                    <div className={"bg-teal-100  lg:w-3/5 lg:h-[66vh]"}>

                    </div>

                </div>
            </div>
        </div>
    )
}

export default MemberProfile;
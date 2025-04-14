import {useAuth} from "../authProvider/useAuth.tsx";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

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

async function getImageOfMember(username, token, imageType) {
    try {
        const response = await fetch(`/api/member/${username}/${imageType}`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        const blob = await response.blob();
        if (!response.ok) {
            return false;
        }
        return URL.createObjectURL(blob);
    } catch (error) {
        console.error("Error loading image:", error);
    }
}


function MemberProfile() {
    const {member, token} = useAuth();
    const [memberByUsername, setMemberByUsername] = useState(null);
    const [avatar, setAvatar] = useState(memberByUsername?.monogram);
    const [banner, setBanner] = useState(null);
    const {username} = useParams();

    useEffect(() => {
        if (!username) return;

        async function fetchMemberByUsername() {
            const memberInPath = await getMemberDataByUsername(username, token);
            setMemberByUsername(memberInPath);
            const avatarImage = await getImageOfMember(username, token, "avatar");
            const bannerImage = await getImageOfMember(username, token, "banner");
            if (avatarImage) {
                setAvatar(avatarImage)
            } else {
                setAvatar(memberInPath.monogram)
            }
            if (bannerImage) setBanner(bannerImage);
        }

        fetchMemberByUsername();
    }, [token, username])


    return (
        <div className={"flex justify-center items-center h-screen"}>
            <div className={"flex flex-col items-center lg:w-3/4 lg:drop-shadow-xl lg:justify-center lg:h-[90vh]"}>
                <div
                    className={"flex h-[40vh] w-full bg-[#D9D9D9] justify-center items-end  lg:bg-white lg:h-2/6 lg:rounded-tl-2xl lg:rounded-tr-2xl"}>
                    {avatar === memberByUsername?.monogram ?
                        <div style={{color: memberByUsername?.monogram.colorCode}}
                             className={"rounded-full w-32 h-32 bg-white flex justify-center items-center absolute -mb-6 lg:w-44 lg:h-44 lg:border-2"}>
                            <p className={"font-medium text-6xl lg:text-8xl"}>{memberByUsername?.monogram.characters}</p>
                        </div>
                        :
                        <div className={"rounded-full w-16 h-16 bg-teal-600"}><img src={avatar} alt={"avatar"}/></div>
                    }
                </div>
                <div className={" lg:flex lg:w-full lg:h-full"}>

                    <div
                        className={"flex flex-col p-8 text-white bg-teal-700 w-screen h-screen lg:w-2/5 lg:h-[66vh] lg:rounded-bl-2xl"}>
                        <div>
                            <p className={"text-xs mb-3"}>Id: {memberByUsername?.memberId}</p>
                        </div>
                        <div>
                            <p className={"text-xs mb-3"}>Member since: {memberByUsername?.dateOfRegistry}</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Username:</p>
                            <p className={"text-l font-bold mb-2"}>{memberByUsername?.username}</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>First name:</p>
                            <p className={"text-l font-bold mb-2"}>{memberByUsername?.firstName}Test</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Last name:</p>
                            <p className={"text-l font-bold mb-2"}>{memberByUsername?.lastName}Test</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Email:</p>
                            <p className={"text-l font-bold mb-2"}>{memberByUsername?.email}</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Birthdate:</p>
                            <p className={"text-l font-bold mb-2"}>{memberByUsername?.birthDate.toString()}</p>
                        </div>
                        <div>
                            <p className={"text-l"}>Biography:</p>
                            <p className={"text-l  mb-6 mt-2 h-24 overflow-auto"}>{memberByUsername?.bio} "Lorem ipsum
                                dolor sit
                                amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
                                magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
                                aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate
                                velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                                proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</p>
                        </div>
                        {username === member?.username &&
                            <button
                                className={" btn bg-[#9EF01A] p-2 hover:bg-accent active:bg-success w-1/3"}>Edit
                            </button>
                        }
                    </div>
                    <div className={"bg-teal-100  lg:w-3/5 lg:h-[66vh]"}>

                    </div>

                </div>
            </div>
        </div>
    )
}

export default MemberProfile;
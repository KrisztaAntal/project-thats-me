import {useEffect, useState} from "react";

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


function MemberProfileHeader({member, token}){
    const [avatar, setAvatar] = useState(member?.monogram);
    const [banner, setBanner] = useState(null);

    useEffect(() => {
        if (!member?.username) return;

        async function fetchAvatarAndBannerOfMember() {
            const avatarImage = await getImageOfMember(member.username, token, "avatar");
            const bannerImage = await getImageOfMember(member.username, token, "banner");
            if (avatarImage) {
                setAvatar(avatarImage)
            } else {
                setAvatar(member.monogram)
            }
            if (bannerImage) setBanner(bannerImage);
        }

        fetchAvatarAndBannerOfMember();
    }, [token, member])


    return (
        <div
            className={"flex h-[40vh] w-full bg-[#D9D9D9] justify-center items-end  lg:bg-white lg:h-2/6 lg:rounded-tl-2xl lg:rounded-tr-2xl"}>
            {avatar === member?.monogram ?
                <div style={{color: member?.monogram.colorCode}}
                     className={"rounded-full w-32 h-32 bg-white flex justify-center items-center absolute -mb-6 lg:w-44 lg:h-44 lg:border-2"}>
                    <p className={"font-medium text-6xl lg:text-8xl"}>{member?.monogram.characters}</p>
                </div>
                :
                <div
                    className={"rounded-full w-32 h-32 bg-teal-600 border-2 border-teal-950 absolute -mb-6 lg:w-44 lg:h-44 "}>
                    <img className={"rounded-full object-cover"} src={avatar} alt={"avatar"}/></div>
            }
        </div>
    )
}

export default MemberProfileHeader;
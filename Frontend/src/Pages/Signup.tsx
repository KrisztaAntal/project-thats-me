import SignupForm from "../Components/SignupForm/SignupForm";

function Signup() {

  return (
    <div className="flex flex-col items-center">
      <h1>Enter your personal details</h1>
      <SignupForm />
    </div>
  )
}

export default Signup;
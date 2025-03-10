import { CustomFormFieldProps } from '../../Types/MemberTypes';

function CustomFormField({ name, inputId, labelValue, inputType, inputValue, onChange, placeholder, onShowPasswordClick, error }: CustomFormFieldProps) {

  return (
    <div className="w-full">
      <div className="w-full justify-between flex relative gap-3 text-sm">
        <label className="text-white text-sm min-w-16" htmlFor={inputId}>{labelValue}</label>
        <input className="shadow-inner-md rounded-md placeholder:justify-center bg-[#CCDBDF] min-h-8 min-w-44 p-1 sm:min-w-52" name={name} id={inputId} type={inputType} value={inputValue} onChange={onChange} placeholder={placeholder} required />
        {labelValue.includes("Password") &&
          <img className='absolute right-1 top-1/2 transform -translate-y-1/2 cursor-pointer '
            src={inputType === "password" ? "./lock-closed.svg" : "./lock-open.svg"}
            alt={inputType === "password" ? 'show password' : 'hide password'}
            title={inputType === "password" ? 'show' : 'hide'}
            onClick={onShowPasswordClick}></img>}
      </div>
      {error && <p className="w-full text-right text-orange-400 text-xs p-1">{error}</p>}
    </div>
  )
}

export default CustomFormField;
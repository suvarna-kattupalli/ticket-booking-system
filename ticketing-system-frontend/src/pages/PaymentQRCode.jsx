import { QRCodeCanvas } from "qrcode.react";

function PaymentQRCode({ amount }) {

  const upiLink =
    `upi://pay?pa=8179575164@ptyes&pn=TicketingSystem&am=${amount}&cu=INR`;

  return (
    <div>
      <h3>Scan to Pay ₹{amount}</h3>

      <QRCodeCanvas
        value={upiLink}
        size={250}
      />
    </div>
  );
}

export default PaymentQRCode;
#ifndef _rukovalac_h_
#define _rukovalac_h_

class Posiljka;

class Rukovalac {
public:
	virtual ~Rukovalac() = default;
	virtual void obradi(Posiljka& p) const = 0;
};

#endif

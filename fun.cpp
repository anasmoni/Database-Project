#include<bits/stdc++.h>
using namespace std;

typedef long long int lli;
typedef pair<int,int> pii;
typedef vector<int> vec;

#define pb push_back
#define mp make_pair
#define mt make_tuple
#define scn(n) scanf("%d",&n)
#define scnll(n) scanf("%lld",&n)
#define scn2(n,m) scanf("%d%d",&n,&m)
#define scn3(n,m,w) scanf("%d%d%d",&n,&m,&w)
#define scn2ll(n,m) scanf("%lld%lld",&n,&m)
#define atoz(v) v.begin(),v.end()
#define Fill(a,v) memset(a,v,sizeof(a))
#define sz(v) v.size()
#define fi first
#define se second
#define inf 1e9
#define pi acos(-1.0)
#define sqr(x) x*x
#define max3(a,b,c) max(a,max(b,c))
#define min3(a,b,c) min(a,min(b,c))


int Set(int N,int pos)
{
    return N=N | (1<<pos);
}
int reset(int N,int pos)
{
    return N= N & ~(1<<pos);
}
bool check(int N,int pos)
{
    return (bool)(N & (1<<pos));
}

bool isnum(char c)
{
    return c>='0' && c<='9';
}

bool ischar(char c)
{
    return c>='A' && c<='z';
}

string all_chars;

int eid,age,salary;
string name="Ebne Sina",address="House-11/A, Road",phone="+880-2-";

string ares = "-2, Dhanmondi R/A, Dhaka-1205",ph="8143312";


vector<string>profession{"Receptionist","Accountant","Cleaner","Cook","Ambulance_Driver","Therapist"};

int sal[10] = {5000,5000,2000,3000,5000,8000};

vector<string>join{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};


string get_add(string s)
{

    for(int i=0; i<s.size(); i++)
    {
        int r = rand();
        if(s[i]=='D')s[i] = (char)('A'+r%26);
        else if(s[i]=='A')s[i] = (char)('A'+r%7);
        else if(ischar(s[i]) && s[i]!='R')
        {
            s[i] = (char)('a'+r%26);
            if(i==0)s[i]=toupper(s[i]);
        }
        else if(isnum(s[i]))s[i] = (char)(r%10+(r%10==0)+'0');
    }

    return s;

}

char roomno(int i){
    if((i>=101 && i<=105) || (i>=1001 && i<=1005))return 'E';

    if((i>=801 && i<=805) || (i>=8001 && i<=8005))return 'M';

    if((i>=201 && i<=230) || (i>=2001 && i<=2030))return 'L';

    if((i>=640 && i<=680) || (i>=6040 && i<=6080))return 'O';

    if((i>=540 && i<=550) || (i>=5040 && i<=5050))return 'I';

    if((i>=501 && i<=530) || (i>=5001 && i<=5030))return 'C';

    if((i>=401 && i<=420) || (i>=4001 && i<=4020))return 'W';

    if((i>=301 && i<=340) || (i>=3001 && i<=3040))return 'D';

    return '$';
}

void sex()
{
    if(rand()%2)cout<<"Male\t";
    else cout<<"Female\t";
}

void tab(int n=1)
{
    cout<<"\t";
}

void employee()
{


    vector<string>emal;
    string s;

    freopen("email.txt","r",stdin);

    while(getline(cin,s))
    {
        emal.push_back(s);
    }


    cout<<"EID\t"<<"Name\t"<<"Address\t"<<"phone\t"<<"Age\t"<<"Profession\t"<<"Salary\t"<<"Gender\t"
        <<"Join_Date\t"<<"EMAIL;";
    ///300-doct , 301-900 nurse, 901-1300,patholo, 1301-2500,staffs

    for(int i=1; i<=2500; i++)
    {

        cout<<i;
        tab();
        int r = rand();
        int z = name.size()-1;
        char c='a';
        z = r%z;
        c  = (char)(c+i%26);

        name[z] = z==0? toupper(c) : name[z]!=' '? c:' ';

        cout<<name;
        tab();

        cout<<"House-"<<get_add("11/A")<<", Road"<<get_add(ares);
        tab();
        cout<<phone+get_add(ph);
        tab();
        cout<<max((i*100)%55,25);
        z = profession.size()-1;
        z = i%z;
        tab();
        //cout<<"xxxxxxxx  "<<z<<" ";
        if(i<=300)
        {
            cout<<"Doctor\t"<<15000<<"\t";
            sex();
        }
        else if(i<=900)cout<<"Nurse\t"<<8000<<"\t"<<"Female\t";
        else if(i<=1300)
        {
            cout<<"Pathologist\t"<<8000<<"\t";
            sex();
        }
        else
        {
            cout<<profession[z];
            tab();
            cout<<sal[z];
            tab();
        }

        cout<<max(i%30,1)<<"-"<<join[i%12]<<"-"<<(1980+i%36);
        tab();
        cout<<emal[i-1];
        cout<<";";

    }


}

void appoin(){
    cout<<"PID\t"<<"EID\t"<<"APP_DATE\t"<<"TIME\t"<<"TYPE;";

    for(int i=1; i<=500; i++){
        cout<<i;
        tab();
        cout<<max(1,rand()%301);
        tab();
        cout<<max(i%30,1)<<"-"<<join[i%12]<<"-"<<2017;
        tab();
        cout<<max(1,rand()%13)<<":"<<rand()%60;
        if(i%2)cout<<" PM";
        else cout<<" AM";
        tab();
        if(i%5==0){
            cout<<get_add("Bipass");
            if(i%2)cout<< " Surgery";
        }
        else cout<<"Visiting";
        cout<<";";
    }
}

void medicine()
{

    cout<<"MID\t"<<"MName\t"<<"Unit_Price\t"<<"Units;";

    for(int i=1; i<=300; i++)
    {
        cout<<i<<"\t";
        cout<<get_add("Omenix-20")<<"mg\t";
        cout<<max((i*999)%1000,40)<<"\t";
        cout<<max(i%20,10)<<";";

    }
}

void patient()
{

    cout<<"PID\t"<<"NAME\t"<<"Age\t"<<"SEX\t"<<"Address\t"<<"Phone\t"<<"Guardian_Contact;";


    for(int i=1; i<=500; i++)
    {
        cout<<i;
        tab();
        int r = rand();
        int z = name.size()-1;
        char c='a';
        z = r%z;
        c  = (char)(c+i%26);

        name[z] = z==0? toupper(c) : name[z]!=' '? c:' ';

        cout<<name;
        tab();
        cout<<max(rand()%100,5);
        tab();
        if(i%2)cout<<"Male";
        else cout<<"Female";
        tab();
        cout<<address+get_add("11/A")+", Road"+get_add(ares);
        tab();
        cout<<phone+get_add(ph);
        tab();
        cout<<phone+get_add(ph)<<";";




    }
}

void presc()
{
    cout<<"PRESCRIPTION_ID\t"<<"PID\t"<<"DID\t"<<"Date_visited\t"<<"DOCTORS_COMMENT;";
    for(int i=1; i<=500; i++)
    {
        cout<<i;
        tab();
        cout<<max(1,rand()%501);
        tab();
        cout<<max(1,rand()%301);
        tab();
        cout<<max(i%30,1)<<"-"<<join[i%12]<<"-"<<(2012+i%6);
        tab();
        cout<<get_add("Avoid alcohol")+" and Come again after "<<max(rand()%60,7)<<"days;";

    }
}

void pres_med()
{
    cout<<"PID\t"<<"DOSE\t"<<"DURATION\t"<<"MNAME;";
    vector<string>dos{"1+0+1","1+1+1","0+0+1","1+0+0","1/2+0+1/2","0+1+0","0+1/2+0",
                      "1/2+0+0","0+0+1/2"};


    vector<string>med;
    string s;

    freopen("med.txt","r",stdin);

    while(getline(cin,s)) med.push_back(s);

    for(int i=1; i<=3000; i++)
    {
        int r= rand();
        cout<<max(r%501,1);
        tab();
        int sx = dos.size();
        sx = r%sx;

        cout<<dos[sx];
        tab();
        if(i%5==0)cout<<"Continued";
        else cout<<max((rand()%13)*5,7)<<" days";
        tab();
        cout<<med[rand()%sz(med)];
        cout<<";";


    }
}

void comp()
{

    //freopen("medic.txt","r",stdin);

    vector<string>co;
    string s;
    while(getline(cin,s))
    {
        co.push_back(s);
    }

    cout<<"PNO\t"<<"MID\t"<<"COMPANY\t"<<"PURCHASE_DATE\t"<<"UNITS;";

    for(int i=1; i<=2000; i++)
    {
        cout<<i;
        tab();
        cout<<max(rand()%301,1);
        tab();
        int x = rand()%59;
        cout<<co[x];
        tab();
        cout<<max(i%30,1)<<"-"<<join[i%12]<<"-"<<(2015+rand()%3);
        tab();
        cout<<max(rand()%31,10)<<";";
    }
}

void nurse()
{

    vector<string>dep;
    freopen("dep.txt","r",stdin);
    string s;

    while(getline(cin,s))dep.pb(s);

    cout<<"EID\t"<<"DEPT\t"<<"TYPE;";

    vector<string>typ{"Registered Nurse","Licensed Practical Nurse","Clinical Nurse Specialist","Nurse Practitioner",
                      "Nurse Case Manager","Intensive Care Unit Registered Nurse",
                      "Travel Registered Nurse","Home Care Registered Nurse","Operating Room Nurse",
                      "Staff Nurse","Emergency Room Registered Nurse","Labor & Delivery Registered Nurse",
                      "Medical/Surgical Registered Nurse","Nurse Supervisor","Oncology Registered Nurse",
                      "Critical Care Registered Nurse","Neonatal Intensive Care Registered Nurse",
                      "Dialysis Registered Nurse","Post-anesthesia Care Unit Registered Nurse"};

    for(int i=301; i<=900; i++)
    {

        cout<<i;
        tab();
        int r = rand()%dep.size();
        cout<<dep[r];
        tab();
        r = rand()%typ.size();
        cout<<typ[r]<<";";

    }

}


void doctor()
{

    vector<string>sp;
    string s;
    freopen("doct_dep.txt","r",stdin);

    while(getline(cin,s))sp.pb(s);


    vector<string>deg{"MBBS","MBBS, FCPS","MBBS, MRCP","MBBS, D.Card., FCPS ",
                      "MBBS, MD ","MBBS, MRCP","MBBS, MCPS ","MBBS, MCPS, MS",
                      "MBBS, DCH, FCPS ","MBBS, DCH","FCPS, MD","MBBS, FCPS, MD, FCAPSC","MBBS, MS"};

    vector<string>typ{"INTERN","ER","SERGEON","SPECIALIST"};

    cout<<"EID\t"<<"SPECIALIZATION\t"<<"TYPE\t"<<"QUALIFICATION;";

    for(int i=1; i<=300; i++)
    {

        cout<<i;
        tab();
        int r = rand()%sp.size();
        cout<<sp[r];
        tab();
        r = rand()%typ.size();
        if(r==0)cout<<"INTERN\t";
        else cout<<typ[r]<<"\t";

        if(r==0)cout<<"MBBS";
        else
        {
            r = rand()%deg.size();
            cout<<deg[r];
        }
        cout<<";";
    }
}

void patho()
{

    vector<string>sp;
    string s;
    freopen("dep.txt","r",stdin);

    while(getline(cin,s))sp.pb(s);


    cout<<"EID\t"<<"DEPT;";

    for(int i=901 ; i<=1300; i++)
    {
        cout<<i;
        tab();

        int r = rand()%sp.size();
        cout<<sp[r];
        cout<<";";

    }
}

void path_assign(){

    cout<<"EID\t"<<"RID;";

    vector<int>v;

    for(int i=1; i<=9000; i++){
        if(roomno(i)=='L')v.pb(i);
    }

    for(int i=901; i<=1300; i++){
        cout<<i;
        tab();
        int g = rand()%sz(v);
        cout<<v[g];
        cout<<";";
    }
}



void room(){

    cout<<"RID\t"<<"TYPE\t"<<"COST\t"<<"DEPT;";

    vector<string>dep;
    freopen("dep.txt","r",stdin);
    string s;

    while(getline(cin,s))dep.pb(s);

    /// 301-340, 3001-3040 DOCTORS_ROOM ,
    /// 401-420,4001-4020 WARD,
    /// 501-530,5001-5030 CABIN,
    /// 540-550,5040-5050 ICU,
    /// 640-680, 6040-6080 OT,
    ///201-230, 2001-2030 LAB
    /// 101-105, 1001-1005 EMERGENCY
    /// 801-805, 8001-8005 MEDICINE_STORE, INVENTORY
    int cnt=0;
    for(int i=101; i<=9000; i++){
        bool oka=0;

        if((101<=i && i<=105) || (i>=1001 && i<=1005)){
            cout<<i;
            tab();
            cout<<"EMERGENCY\t"<<max((99999*rand())%200000,1000)<<"\t";
            oka=1;
        }
        if((201<=i && i<=230) || (i>=2001 && i<=2030)){
            cout<<i;
            tab();
            cout<<"LAB\t\t";
            oka=1;
        }
        if((640<=i && i<=680) || (i>=6040 && i<=6080)){
            cout<<i;
            tab();
            cout<<"OT\t"<<max((99999*rand())%500000,10000)<<"\t";
            oka=1;
        }

        if((540<=i && i<=550) || (i>=5040 && i<=5050)){
            cout<<i;
            tab();
            cout<<"ICU\t"<<"2000\t";
            oka=1;
        }
        if((801<=i && i<=805) || (i>=8001 && i<=8005)){
            cout<<i;
            tab();
            if(i%2)cout<<"MEDICINE_STORE\t\t";
            else cout<<"INVENTORY\t\t";
            oka=1;
        }
        if((501<=i && i<=530) || (i>=5001 && i<=5030)){
            cout<<i;
            tab();
            cout<<"CABIN\t"<<(1200+(i%5)*100)<<"\t";
            oka=1;
        }
        if((401<=i && i<=420) || (i>=4001 && i<=4020)){
            cout<<i;
            tab();
            cout<<"WARD\t"<<(600+(i%5)*100)<<"\t";
            oka=1;
        }
        if((301<=i && i<=340) || (i>=3001 && i<=3040)){
            cout<<i;
            tab();
            cout<<"WARD\t"<<(600+(i%5)*100)<<"\t";
            oka=1;
        }


        if(oka){
            int r = rand()%dep.size();
            cout<<dep[r];
            cout<<";";
            cnt++;
        }

    }
    //cout<<endl<<cnt<<endl;
}

void dass(){

    cout<<"DID\t"<<"RID;";

    map<int,bool>per;

    freopen("per_doct.txt","r",stdin);
    int x;

    while(scanf("%d",&x)==1){
        per[x]=true;
    }

    vector<int>v,w;

    for(int i=1; i<=9000; i++){
        if(roomno(i)=='D')v.pb(i);
        else if(roomno(i)=='W' || roomno(i)=='O' || roomno(i)=='E')w.pb(i);
    }

    for(int i=1,idx=0; i<=300; i++){
        cout<<i<<"\t";
        if(per[i] && idx<sz(v))cout<<v[idx++]<<"\t";
        else {
            int ix = rand()%sz(w);
            cout<<w[ix]<<"\t";
        }
        cout<<";";
    }

}
void eass(){

    cout<<"EID\t"<<"RID\t"<<"SHIFT;";

    vector<int>v;

    for(int i=1; i<=9000; i++){
        if(roomno(i)!='$')v.pb(i);
    }

    for(int i=1301; i<=2500; i++)
    {
        cout<<i;
        tab();
        cout<<v[(rand()*i)%sz(v)];
        tab();
        if(i%2)cout<<"Night";
        else cout<<"Day";
        cout<<";";
    }
}

void Nass(){

    cout<<"EID\t"<<"RID\t"<<"SHIFT;";

    vector<int>v;

    for(int i=1; i<=9000; i++){
        if(roomno(i)!='$' && roomno(i)!='M' && roomno(i)!='D')v.pb(i);
    }

    for(int i=301; i<=900; i++)
    {
        cout<<i;
        tab();
        int idx = (i*rand())%sz(v);
        cout<<v[idx];
        tab();
        if(i%2)cout<<"Night";
        else cout<<"Day";
        cout<<";";
    }
}

void testhist(){

    cout<<"TEST_NAME\t"<<"PRES_ID\t"<<"TEST_DATE\t"<<"REPORT_DATE;";

    vector<string>tes;
    string s;
    freopen("tests.txt","r",stdin);

    while(getline(cin,s))tes.pb(s);
    //cout<<tes.size();
    for(auto x : tes){
        cout<<x;
        tab();
        cout<<max(rand()%501,1);
        tab();
        int r = max(rand()%20,1);
        cout<<r<<"-"<<join[r%12]<<"-"<<2017;
        tab();
        cout<<r+2<<"-"<<join[r%12]<<"-"<<2017;
        cout<<";";

        cout<<x;
        tab();
        cout<<max(rand()%501,1);
        tab();
        r = max(rand()%20,1);
        cout<<r<<"-"<<join[r%12]<<"-"<<2017;
        tab();
        cout<<r+2<<"-"<<join[r%12]<<"-"<<2017;
        cout<<";";

    }

}

void stays(){

    cout<<"PID\t"<<"RID\t"<<"BID\t"<<"START_DATE\t"<<"END_DATE;";

    vector<int>v;

    for(int i=1; i<=9000; i++){
        if(roomno(i)=='I' || roomno(i)=='C' || roomno(i)=='o'|| roomno(i)=='E')v.pb(i);
        if(roomno(i)=='W')v.pb(-i);
    }

    for(int i=1; i<=500; i++){
        cout<<i;
        tab();
        int x = (i*rand())%sz(v);
        x = v[x];

        if(x>0)cout<<x<<"\t"<<x;

        else {
            x = -x;
            cout<<x<<"\t"<<x<<get_add("A");
        }
        tab();

        int r = max(rand()%20,1);
        cout<<r<<"-"<<join[r%5]<<"-"<<2017;
        tab();
        r = max(rand()%20,1);
        cout<<r<<"-"<<join[5+r%7]<<"-"<<2017;
        cout<<";";

    }
}

void tests(){
     cout<<"TEST_NAME\t"<<"LAB_NO\t"<<"COST;";

    vector<string>tes;
    string s;
    freopen("tests.txt","r",stdin);

    while(getline(cin,s))tes.pb(s);

    vector<int>lno;

    for(int i=201; i<=4000; i++){
        if(roomno(i)=='L')lno.pb(i);
    }


    for(auto x : tes){
        cout<<x;
        tab();
        int idx = rand()%lno.size();
        cout<<lno[idx];
        tab();
        idx = (rand()%2000)/10;

        cout<<max(100,idx*10)<<";";
    }
}

void email(){

    map<string,bool>tak;
    cout<<"EID\t"<<"EMAIL;";
    string g = "mohammadanas.cse" , e = "@gmail.com";
    cout<<"1\t"<<g+e<<";";
    tak[g+e]=true;


    for(int i=2; i<=2500; ){
        string temp="";
        cout<<i;
        tab();
        for(int j=0; j<10; j++){
            temp += (char)(rand()%26 + 'a');
        }
        temp += e;
        if(tak[temp]==false){
           tak[temp]=true;
           cout<<temp<<";";
           i++;
        }
    }
}

string randString(string s){
    for(int i=0; i<s.size(); i++){
            if(s[i]==' ')continue;
        int r = rand()*10;
        r = r%all_chars.size();
        s[i]  = all_chars[r];
    }
    return s;
}

string randNum(string s){
    for(int i=0; i<s.size(); i++){

        int r = rand()*10;
        r = r%11;
        s[i]  = '0'+r;
    }
    return s;
}

void UserPass(){

    cout<<"EID\t"<<"USERNAME\t"<<"PASSWORD;";

    for(int i=1 ; i<=300; i++){
        cout<<i;
        tab();
        cout<<get_add("anasmoni");
        tab();
        cout<<get_add("kutimali");
        cout<<";";
    }
    for(int i=900; i<=1300; i++){
        cout<<i;
        tab();
        cout<<get_add("anasmoni");
        tab();
        cout<<get_add("kutimali");
        cout<<";";
    }

    freopen("DOCTASS.txt","r",stdin);
    int d;
    while(cin>>d){
        cout<<d;
        tab();
        cout<<get_add("anasmoni");
        tab();
        cout<<get_add("kutimali");
        cout<<";";
    }
}



void Dept(){

    vector<string>sp;
    string s;
    freopen("dep.txt","r",stdin);

    while(getline(cin,s))sp.pb(s);

    cout<<"EID\t"<<"DEPT;";

    for(int i=2501; i<=2600; i++)
    {

        cout<<i;
        tab();
        int r = rand()%sp.size();
        cout<<sp[r];
        cout<<";";
    }
}


void Admin()
{


    vector<string>emal;
    map<string,bool>emap;
    string e = "@gmail.com" , s;

    for(int i=1; i<=59; i++){
        s = "anasmoni";

        while(emap[s]){
            s = get_add(s);
        }

        emap[s] = true;
        emal.pb(s);
    }

    vector<string>sp;
    freopen("dep.txt","r",stdin);
    while(getline(cin,s))sp.pb(s);


    cout<<"EID\t"<<"Name\t"<<"Address\t"<<"phone\t"<<"Age\t"<<"Profession\t"<<"Salary\t"<<"Gender\t"
        <<"Join_Date\t"<<"EMAIL\t"<<"DEPT;";
    ///300-doct , 301-900 nurse, 901-1300,patholo, 1301-2500,staffs

    for(int i=2501,j=0; i<=2559;j++, i++)
    {

        cout<<i;
        tab();
        int r = rand();
        int z = name.size()-1;
        char c='a';
        z = r%z;
        c  = (char)(c+i%26);

        name[z] = z==0? toupper(c) : name[z]!=' '? c:' ';

        cout<<name;
        tab();

        cout<<"House-"<<get_add("11/A")<<", Road"<<get_add(ares);
        tab();
        cout<<phone+get_add(ph);
        tab();
        cout<<max((i*100)%55,25);
        tab();
        cout<<"Admin";
        tab();
        cout<<"20000\tMale\t";
        cout<<max(i%30,1)<<"-"<<join[i%12]<<"-"<<(1980+i%36);
        tab();
        cout<<emal[j]+e;
        tab();
        cout<<sp[j]<<";";

    }


}

void deg(){

    cout<<"EID\t"<<"DEGREE;";

    vector<string>deg{"MBBS","MBBS, FCPS","MBBS, MRCP","MBBS, D.Card., FCPS ",
                      "MBBS, MD ","MBBS, MRCP","MBBS, MCPS ","MBBS, MCPS, MS",
                      "MBBS, DCH, FCPS ","MBBS, DCH","FCPS, MD","MBBS, FCPS, MD, FCAPSC","MBBS, MS"};

    for(int i=1; i<=2559; i++){
        cout<<i;
        tab();
        if(i>300)cout<<get_add("BA.HOns,MA");
        else cout<<deg[rand()%sz(deg)];
        cout<<";";
    }
}

void adminupass(){

    cout<<"EID\t"<<"USERNAME\t"<<"PASSWORD;";

    for(int i=2501 ; i<=2559; i++){
        cout<<i;
        tab();
        cout<<get_add("asdasdanasmoni");
        tab();
        cout<<get_add("kutimasdsdfsdf");
        cout<<";";
    }
}


void temo(){

    vector<string>sp;
    string s;
    freopen("dep.txt","r",stdin);

    while(getline(cin,s))sp.pb(s);

    cout<<"EID\t"<<"DEPT;";

    for(int i=1; i<=500; i++)
    {

        cout<<i;
        tab();
        int r = rand()%sp.size();
        cout<<sp[r];
        cout<<";";
    }

}

int main()
{

    for(int i=0; i<26; i++){
        all_chars += 'a'+i;
    }

    for(int i=0; i<26; i++){
        all_chars += 'A'+i;
    }
    for(int i=0; i<10; i++){
        all_chars += '0'+i;
    }


    freopen("tempo.txt","w",stdout);

    //email();
    //Dept();
    //deg();
   // adminupass();
   //pres_med();
   temo();
    //UserPass();


    return 0;
}
